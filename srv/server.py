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


@app.route('/user', methods=['PUT'])
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


@app.route('/user/<string:columnName>/<string:columnValue>', methods=['POST'])
@authorized
def editUser(columnName, columnValue):
    user = Users(get_database())

    status, message = user.editUser(columnName, columnValue, get_user_id())

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/user/photo', methods=['POST'])
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


@app.route('/cards/searchedCards', methods=['POST'])
def getSearchedCards():
    card = Cards(get_database())
    params = request.get_json()
    searchedQuery = params.get('recipeName')

    userID = get_user_id()
    if not userID:
        userID = -1

    cards = card.getSearchedCardsSortedByDefault(searchedQuery, userID)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)

@app.route('/cards/categoryCards', methods=['POST'])
def getCategoryCards():
    card = Cards(get_database())
    params = request.get_json()
    recipeCategory = params.get('recipeCategory')

    userID = get_user_id()
    if not userID:
        userID = -1

    cards = card.getCategoryCardsSortedByDefault(recipeCategory, userID)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)

@app.route('/cards/<int:recipeId>', methods=['GET'])
def getUpdatedCard(recipeId):
    card = Cards(get_database())

    userID = get_user_id()
    if not userID:
        userID = -1

    cards = card.getUpdatedCard(userID, recipeId)

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
    elif sorted_method == 'userCards':
        cards = card.getAllCardsSortedByUser(userID)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/recipe/<int:recipeId>', methods=['GET'])
def getRecipe(recipeId):
    recipe = Recipes(get_database())

    recipes = recipe.getRecipe(recipeId)

    if not recipes:
        Logger.fail("There was no recipe returned!")
    return jsonify(recipes)


@app.route('/recipe', methods=['PUT'])
@authorized
def addRecipe():
    recipe = Recipes(get_database())
    userID = get_user_id()

    params = request.get_json()

    recipeName = params.get('recipeName')
    recipePrepareTime = params.get('recipePrepareTime')
    recipeCookTime = params.get('recipeCookTime')
    recipeBakeTime = params.get('recipeBakeTime')
    recipeMainPicture = params.get('recipeMainPicture')
    recipeCategory = params.get('recipeCategory')


    status, message = recipe.addRecipe(userID, recipeName,
                                        recipePrepareTime, recipeCookTime, recipeBakeTime,
                                        recipeMainPicture, recipeCategory)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/allElements', methods=['PUT'])
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
    statusAddingStars, messageAddingStars = star.addStars(userID, recipeId, starsList)

    ingredientList = params['ingredientList']
    statusAddingIngredient, messageAddingIngredient = ingredient.addIngredient(recipeId, ingredientList)

    stepList = params['stepList']
    statusAddingStep, message = step.addStep(recipeId,stepList)

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


@app.route('/recipe/<int:recipeId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editRecipe(recipeId, columnName, columnValue):
    recipe = Recipes(get_database())

    status, message = recipe.editRecipe(columnName, columnValue, recipeId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteRecipe(recipeId):
    recipe = Recipes(get_database())

    status, message = recipe.deleteRecipe(recipeId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/photo/<int:photoId>', methods=['GET'])
def getPhoto(photoId):
    photo = Photo(get_database())

    photos = photo.getPhoto(photoId)

    if not photos:
        Logger.fail("There was no photo returned!")
    return photos

@app.route('/recipe/photo/<int:photoId>/', methods=['POST'])
@authorized
def editPhoto(photoId):
    photo = Photo(get_database())

    params = request.get_json()

    photos = params.get('photo')

    status, message = photo.editPhoto(photoId, photos)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/photo/<int:photoId>', methods=['DELETE'])
@authorized
def deletePhoto(photoId):
    photo = Photo(get_database())

    status, message = photo.deletePhoto(photoId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/step/<int:recipeId>', methods=['GET'])
def getStep(recipeId):
    step = Steps(get_database())

    steps = step.getStep(recipeId)

    if not steps:
        Logger.fail("There was no recipe returned!")
    return jsonify(steps)


@app.route('/recipe/step/<int:stepId>', methods=['DELETE'])
@authorized
def deleteStep(stepId):
    step = Steps(get_database())

    status, message = step.deleteStep(stepId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/step/all/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteAllSteps(recipeId):
    step = Steps(get_database())

    status, message = step.deleteAllStep(recipeId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/step', methods=['PUT'])
@authorized
def addStep():
    step = Steps(get_database())

    params = request.get_json()

    recipeId = params.get('recipeId')
    photo = params.get('photo')
    stepNumber = params.get('stepNumber')
    stepDescription = params.get('stepDescription')

    status, message = step.addStep(recipeId, photo, stepNumber,
                  stepDescription)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/step/<int:stepId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editStep(stepId, columnName, columnValue):
    step = Steps(get_database())

    status, message = step.editStep(columnName, columnValue, stepId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/ingredient/<int:recipeId>', methods=['GET'])
def getIngredient(recipeId):
    ingredient = Ingredients(get_database())

    ingredients = ingredient.getIngredient(recipeId)

    if not ingredients:
        Logger.fail("There was no ingredients returned!")
    return jsonify(ingredients)


@app.route('/recipe/ingredient/<int:ingredientId>', methods=['DELETE'])
@authorized
def deleteIngredient(ingredientId):
    ingredient = Ingredients(get_database())

    status, message = ingredient.deleteIngredient(ingredientId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/ingredient/all/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteAllIngredients(recipeId):
    ingredient = Ingredients(get_database())

    status, message = ingredient.deleteAllIngredient(recipeId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/ingredient', methods=['PUT'])
@authorized
def addIngredient():
    ingredient = Ingredients(get_database())

    params = request.get_json()

    recipeId = params.get('recipeId')
    ingredientQuantity = params.get('ingredientQuantity')
    ingredientUnit = params.get('ingredientUnit')
    ingredientName = params.get('ingredientName')
    ingredientGroup = params.get('ingredientGroup')

    status, message = ingredient.addIngredient(recipeId, ingredientQuantity, ingredientUnit,
                      ingredientName, ingredientGroup)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/ingredient/<int:ingredientId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editIngredient(ingredientId, columnName, columnValue):
    ingredient = Ingredients(get_database())

    status, message = ingredient.editIngredient(columnName, columnValue, ingredientId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/comment/<int:recipeId>', methods=['GET'])
def getComment(recipeId):
    comment = Comments(get_database())

    comments = comment.getComments(recipeId)

    return jsonify(comments)


@app.route('/recipe/comment/<int:commentId>', methods=['DELETE'])
@authorized
def deleteComment(commentId):
    comment = Comments(get_database())

    status, message = comment.deleteComment(commentId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/comment/all/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteAllComments(recipeId):
    comment = Comments(get_database())

    status, message = comment.deleteAllComment(recipeId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/comment', methods=['PUT'])
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


@app.route('/recipe/comment/<int:commentId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editComment(commentId, columnName, columnValue):
    comment = Comments(get_database())

    status, message = comment.editComment(columnName, columnValue, commentId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/stars/<int:recipeId>', methods=['GET'])
def getStars(recipeId):
    star = Stars(get_database())
    userID = get_user_id()

    stars = star.getStars(recipeId, userID)

    if not stars:
        Logger.fail("There was no stars returned!")
    return jsonify(stars)


@app.route('/recipe/favorite/<int:recipeId>', methods=['GET'])
def getFavorite(recipeId):
    star = Stars(get_database())
    userID = get_user_id()

    favorite = star.getFavorite(recipeId, userID)

    if not favorite:
        Logger.fail("There was no stars returned!")
    return jsonify(favorite)

@app.route('/recipe/stars/detail/<int:recipeId>', methods=['GET'])
def getRecipeDetailsStars(recipeId):
    star = Stars(get_database())

    userID = get_user_id()
    if not userID:
        userID = -1

    stars = star.getRecipeDetailsStars(userID, recipeId)

    if not stars:
        Logger.fail("There was no stars returned!")
    return jsonify(stars)


@app.route('/recipe/stars/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteStars(recipeId):
    star = Stars(get_database())
    userID = get_user_id()

    status, message = star.deleteStars(recipeId, userID)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/stars/all/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteAllStars(recipeId):
    star = Stars(get_database())

    status, message = star.deleteAllStars(recipeId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/stars', methods=['PUT'])
@authorized
def addStars():
    star = Stars(get_database())
    userID = get_user_id()

    params = request.get_json()

    starsCount = params.get('starsCount')
    favoritesCount = params.get('favoritesCount')
    recipeId = params.get('recipeId')

    status, message = star.addStars(userID, recipeId, starsCount, favoritesCount)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/stars/<int:recipeId>/<string:columnName>/<int:columnValue>', methods=['POST'])
@authorized
def editStars(recipeId, columnName, columnValue):
    star = Stars(get_database())
    userID = get_user_id()

    status, message = star.editStars(columnName, columnValue, recipeId, userID)

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
