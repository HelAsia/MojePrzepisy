#!flask/bin/python

from functools import wraps
from flask import Flask, jsonify, request, session, g
from flask_gzip import Gzip

import os

# My libraries
from Constans import *
from Database import *
from Session import *
from Users import *
from recipe_elements.Cards import *
from recipe_elements.Recipes import *
from recipe_elements.Steps import *
from recipe_elements.Ingredients import *
from recipe_elements.Comments import *
from recipe_elements.Stars import *
from recipe_elements.Photo import *

# Global definitions
app = Flask(__name__)
gzip = Gzip(app)

global_query_id = 1000


# -----------------------------
# Utilities

def request_has_database():
    return hasattr(g, 'database')


def get_database():
    if not request_has_database():
        Logger.info("Acquiring new database connection to server GLOBALS")
        g.database = Database(global_query_id)
        if not g.database.connection(host=HOST, user=USER, password=PASSWORD, db=DATABASE):
            Logger.err("Database connection failed")
            return None

    return g.database


@app.teardown_request
def close_db_connection(ex):
    global global_query_id
    if request_has_database():
        conn = get_database()
        # Rollback
        # Alternatively, you could automatically commit if ex is None
        # and rollback otherwise, but I question the wisdom
        # of automatically committing.
        Logger.info("Closing GLOBAL database connection.")
        if conn:
            conn.close()

        global_query_id += 1000


def authorized(f):
    '''
        This is a helpful decorator to be used when user's authentication
    should be enforced upon entering endpoint's route. It does it's job by
    creating Session's object and validating that session.

    returns:
        If session is valid - the decorated function becomes called and it's
        value is returned.

        Otherwise, the Unauthorized JSON is returned:
            {
                "status": 401,
                "message": "Unauthorized. Please login first."
            }
    '''

    @wraps(f)
    def validator(*args, **kwargs):
        sess = Session(session, get_database())
        Logger.dbg("validator: Request headers:\n=====\n{}\n=====\n".format(request.headers))
        if not sess.validate_session():
            Logger.fail('@authorized: User not authenticated.')
            return jsonify({
                'status': 401,
                'message': 'Unauthorized. Please login first.'
            })
        else:
            Logger.ok('@authorized: User authenticated.')
            return f(*args, **kwargs)

    return validator


def get_user_id():
    sess = Session(session, get_database())
    return sess.get_user_id()


# -----------------------------
# Server code

# Login session
@app.route('/login', methods=['POST'])
def login_method():
    user = Users(get_database())
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    status, message, userID = user.loginUser(login, password)

    if status == 200:
        sess = Session(session, get_database())
        sess.create_session(userID)

        Logger.dbg('login(): userID = {}'.format(str(userID)))
        Logger.ok('User "{}" has been logged.'.format(login))
    else:
        Logger.fail('Could not authenticate user: "{}"'.format(login))

    return jsonify({
        'status': status,
        'message': userID
    })


# Logout session
@app.route('/logout', methods=['GET'])
@authorized
def logout_method():
    sess = Session(session, get_database())
    sess.destroySession()

    return jsonify({
        'status': 200,
        'message': 'You have been logged out.'
    })


@app.route('/user', methods=['POST'])
def registration():
    user = Users(get_database())
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')
    firstName = params.get('firstName')
    lastName = params.get('lastName')
    email = params.get('email')

    status, message = user.registerUser(login, password, firstName, lastName, email)

    return jsonify({
        'status': status,
        'message': message
    })


# Operations performed on the profile
@app.route('/user', methods=['GET', 'DELETE'])
@authorized
def profile_method():
    user = Users(get_database())

    # Show user data
    if request.method == 'GET':
        return jsonify(user.getUser(get_user_id()))

    # Delete user
    elif request.method == 'DELETE':
        status, message = user.deleteUser(get_user_id())

        return jsonify({
            'status': status,
            'message': message
        })


@app.route('/user/<string:columnName>/<string:columnValue>', methods=['PUT'])
@authorized
def editUser(columnName, columnValue):
    user = Users(get_database())

    status, message = user.editUser(columnName, columnValue, get_user_id())

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/user/photo', methods=['PUT'])
@authorized
def editUserPhoto():
    user = Users(get_database())

    params = request.get_json()
    userPhoto = params.get('userPhoto')

    status, message = user.editUserPhoto(userPhoto, get_user_id())

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/cards/searched/<string:recipeName>', methods=['GET'])
def getSearchedCards(recipeName):
    card = Cards(get_database())

    userID = get_user_id()
    if not userID:
        userID = -1

    cards = card.getSearchedCardsSortedByDefault(recipeName, userID)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/cards/category/<string:categoryName>', methods=['GET'])
def getCategoryCards(categoryName):
    card = Cards(get_database())

    userID = get_user_id()
    if not userID:
        userID = -1

    cards = card.getCategoryCardsSortedByDefault(categoryName, userID)

    if not cards:
        return jsonify([])
        Logger.fail("There were no cards returned!")
    else:
        return jsonify(cards)


@app.route('/cards/<int:id>', methods=['GET'])
def getUpdatedCard(id):
    card = Cards(get_database())

    userID = get_user_id()
    if not userID:
        userID = -1

    cards = card.getUpdatedCard(userID, id)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/cards/<string:sorted_method>', methods=['GET'])
def getSortedCards(sorted_method):
    card = Cards(get_database())
    userID = get_user_id()
    if not userID:
        userID = -1

    if sorted_method == 'default':
        cards = card.getAllCards(userID)
    elif sorted_method == 'alphabetically':
        cards = card.getAllCardsSortedAlphabetically(userID)
    elif sorted_method == 'lastAdded':
        cards = card.getAllCardsSortedByLastAdded(userID)
    elif sorted_method == 'highestRated':
        cards = card.getAllCardsSortedByHighestRated(userID)
    elif sorted_method == 'favorite':
        cards = card.getAllCardsSortedByFovorite(userID)
    elif sorted_method == 'user':
        cards = card.getAllCardsSortedByUser(userID)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/cards/new/<int:maxDate>', methods=['GET'])
def getNewCards(maxDate):
    card = Cards(get_database())
    userID = get_user_id()
    if not userID:
        userID = -1

    Logger.dbg("Received following maxDate: {}".format(maxDate))
    cards = card.getNewCards(userID, maxDate)

    if not cards:
        return jsonify({
            'status': 404,
            'message': u'There are no new cards! '
        })
    else:
        return jsonify({
            'status': 200,
            'message': u'There are new cards!'
        })


@app.route('/recipe/<int:id>', methods=['GET'])
def getRecipe(id):
    recipe = Recipes(get_database())

    recipes = recipe.getRecipe(id)

    if not recipes:
        Logger.fail("There was no recipe returned!")
    return jsonify(recipes)


@app.route('/recipe', methods=['POST'])
@authorized
def addWholeRecipeElements():
    recipe = Recipes(get_database())
    star = Stars(get_database())
    ingredient = Ingredients(get_database())
    step = Steps(get_database())
    userID = get_user_id()

    params = request.get_json()

    recipeList = params['recipeList'][0]
    statusAddingRecipe, recipeId = recipe.addRecipe(userID, recipeList)

    starsList = params['starsList'][0]
    statusAddingStars, messageAddingStars = star\
        .addStars(userID, recipeId, starsList['starsCount'], starsList['favoritesCount'])

    ingredientList = params['ingredientList']
    statusAddingIngredient, messageAddingIngredient = ingredient.addIngredient(recipeId, ingredientList)

    stepList = params['stepList']
    statusAddingStep, messageAddingStep = step.addStep(recipeId,stepList)

    if statusAddingRecipe == 200 and statusAddingIngredient == 200 and statusAddingStep == 200 and statusAddingStars == 200:
        return jsonify({
            'status': 200,
            'message': u'Whole recipe elements were added. '
        })
    else:
        return jsonify({
            'status': 404,
            'message': u'Forwarded data are not correct'
        })


@app.route('/recipe/<int:id>/<string:columnName>/<string:columnValue>/', methods=['PUT'])
@authorized
def editRecipe(id, columnName, columnValue):
    recipe = Recipes(get_database())

    status, message = recipe.editRecipe(columnName, columnValue, id)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/<int:id>', methods=['DELETE'])
@authorized
def deleteRecipe(id):
    recipe = Recipes(get_database())

    status, message = recipe.deleteRecipe(id)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/photo/<int:id>', methods=['GET'])
def getPhoto(id):
    photo = Photo(get_database())

    photos = photo.getPhoto(id)

    if not photos:
        Logger.fail("There was no photo returned!")
    return photos

@app.route('/recipe/photo/<int:id>/', methods=['PUT'])
@authorized
def editPhoto(id):
    photo = Photo(get_database())

    params = request.get_json()

    photos = params.get('photo')

    status, message = photo.editPhoto(id, photos)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/photo/<int:id>', methods=['DELETE'])
@authorized
def deletePhoto(id):
    photo = Photo(get_database())

    status, message = photo.deletePhoto(id)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/photo', methods=['POST'])
@authorized
def addPhoto():
    photo = Photo(get_database())

    photoString = request.form['photo']

    status, photoNumber = photo.addPhoto(photoString)

    return jsonify({
        'status': status,
        'message': photoNumber
    })

@app.route('/recipe/<int:id>/step', methods=['GET'])
def getStep(id):
    step = Steps(get_database())

    steps = step.getStep(id)

    if not steps:
        Logger.fail("There was no recipe returned!")
    return jsonify(steps)


@app.route('/recipe/step/<int:id>', methods=['DELETE'])
@authorized
def deleteStep(id):
    step = Steps(get_database())

    status, message = step.deleteStep(id)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/step/<int:id>/<string:columnName>/<string:columnValue>/', methods=['PUT'])
@authorized
def editStep(id, columnName, columnValue):
    step = Steps(get_database())

    status, message = step.editStep(columnName, columnValue, id)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/<int:id>/ingredient', methods=['GET'])
def getIngredient(id):
    ingredient = Ingredients(get_database())

    ingredients = ingredient.getIngredient(id)

    if not ingredients:
        Logger.fail("There was no ingredients returned!")
    return jsonify(ingredients)


@app.route('/recipe/ingredient/<int:id>', methods=['DELETE'])
@authorized
def deleteIngredient(ingredientId):
    ingredient = Ingredients(get_database())

    status, message = ingredient.deleteIngredient(ingredientId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/ingredient/<int:id>/<string:columnName>/<string:columnValue>/', methods=['PUT'])
@authorized
def editIngredient(id, columnName, columnValue):
    ingredient = Ingredients(get_database())

    status, message = ingredient.editIngredient(columnName, columnValue, id)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/<int:id>/comment', methods=['GET'])
def getComment(id):
    comment = Comments(get_database())

    comments = comment.getComments(id)

    return jsonify(comments)


@app.route('/recipe/comment/<int:id>', methods=['DELETE'])
@authorized
def deleteComment(id):
    comment = Comments(get_database())

    status, message = comment.deleteComment(id)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/<int:id>/comment/all', methods=['DELETE'])
@authorized
def deleteAllComments(id):
    comment = Comments(get_database())

    status, message = comment.deleteAllComment(id)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/comment', methods=['POST'])
@authorized
def addComment():
    comment = Comments(get_database())

    params = request.get_json()

    recipeId = params.get('recipeId')
    commentText = params.get('comment')

    status, message = comment.addComment(recipeId, get_user_id(), commentText)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/comment/<int:id>/<string:columnValue>', methods=['PUT'])
@authorized
def editComment(id, columnValue):
    comment = Comments(get_database())

    status, message = comment.editComment(columnValue, id)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/<int:id>/stars', methods=['GET'])
def getStars(id):
    star = Stars(get_database())
    userID = get_user_id()

    stars = star.getStars(id, userID)

    if not stars:
        Logger.fail("There was no stars returned!")
    return jsonify(stars)


@app.route('/recipe/<int:id>/favorite', methods=['GET'])
def getFavorite(id):
    star = Stars(get_database())
    userID = get_user_id()

    favorite = star.getFavorite(id, userID)

    if not favorite:
        Logger.fail("There was no stars returned!")
    return jsonify(favorite)


@app.route('/recipe/<int:id>/stars/detail', methods=['GET'])
def getRecipeDetailsStars(id):
    star = Stars(get_database())

    userID = get_user_id()
    if not userID:
        userID = -1

    stars = star.getRecipeDetailsStars(userID, id)

    if not stars:
        Logger.fail("There was no stars returned!")
    return jsonify(stars)


@app.route('/recipe/<int:id>/stars', methods=['DELETE'])
@authorized
def deleteStars(id):
    star = Stars(get_database())
    userID = get_user_id()

    status, message = star.deleteStars(id, userID)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/stars', methods=['POST'])
@authorized
def addStars():
    star = Stars(get_database())
    userID = get_user_id()

    params = request.get_json()

    starsCount = params.get('starsCount')
    favoritesCount = params.get('favoritesCount')
    id = params.get('id')

    status, message = star.addStars(userID, id, starsCount, favoritesCount)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/<int:id>/stars/<string:columnName>/<int:columnValue>', methods=['PUT'])
@authorized
def editStars(id, columnName, columnValue):
    star = Stars(get_database())
    userID = get_user_id()

    status, message = star.editStars(columnName, columnValue, id, userID)

    return jsonify({
        'status': status,
        'message': message
    })


def main():
    database = Database()
    if not database.connection(host=HOST, user=USER, password=PASSWORD, db=DATABASE):
        Logger.err("Database connection failed")
        return

    database.close()

    # This launches server
    app.secret_key = os.urandom(32)
    app.run(debug=True)


if __name__ == '__main__':
    main()
