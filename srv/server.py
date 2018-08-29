#!flask/bin/python

from functools import wraps
from flask import Flask, jsonify, request, session

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

# Global definitions
app = Flask(__name__)
database = None


# -----------------------------
# Utilities

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
        sess = Session(session, database)
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
    sess = Session(session, database)
    return sess.get_user_id()


# -----------------------------
# Server code

# Login session
@app.route('/login', methods=['POST'])
def login_method():
    user = Users(database)
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    status, message, userID = user.loginUser(login, password)

    if status == 200:
        sess = Session(session, database)
        sess.create_session(userID)

        Logger.dbg('login(): userID = {}'.format(str(userID)))
        Logger.ok('User "{}" has been logged.'.format(login))
    else:
        Logger.fail('Could not authenticate user: "{}"'.format(login))

    return jsonify({
        'status': status,
        'message': message
    })


# Logout session
@app.route('/logout', methods=['GET'])
@authorized
def logout_method():
    sess = Session(session, database)
    sess.destroySession()

    return jsonify({
        'status': 200,
        'message': 'You have been logged out.'
    })


@app.route('/user', methods=['PUT'])
def registration():
    user = Users(database)
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
    user = Users(database)

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
    user = Users(database)

    status, message = user.editUser(columnName, columnValue, get_user_id())

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/cards/searchedCards', methods=['POST'])
def getSearchedCards():
    card = Cards(database)
    params = request.get_json()
    searchedQuery = params.get('recipeName')

    cards = card.getSearchedCardsSortedByDefault(searchedQuery)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/cards/<string:sorted_method>', methods=['GET'])
def getSortedCards(sorted_method):
    card = Cards(database)
    if sorted_method == 'default':
        cards = card.getAllCards()
    elif sorted_method == 'alphabetically':
        cards = card.getAllCardsSortedAlphabetically()
    elif sorted_method == 'lastAdded':
        cards = card.getAllCardsSortedByLastAdded()
    elif sorted_method == 'highestRated':
        cards = card.getAllCardsSortedByHighestRated()

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/recipe/id', methods=['GET'])
def getRecipeId():
    recipe = Recipes(database)
    user = Users(database)
    params = request.get_json()

    userID = user.getUser(get_user_id())
    recipeName = params.get('recipeName')
    recipeCategory = params.get('recipeCategory')
    recipePrepareTime = params.get('recipePrepareTime')
    recipeCookTime = params.get('recipeCookTime')
    recipeBakeTime = params.get('recipeBakeTime')


    recipeId = recipe.getRecipeId(userID, recipeName, recipePrepareTime,
                                  recipeCookTime, recipeBakeTime, recipeCategory)

    return jsonify({
        'recipeId': recipeId,
    })



@app.route('/recipe/<int:recipeId>', methods=['GET'])
def getRecipe(recipeId):
    recipe = Recipes(database)

    recipes = recipe.getRecipe(recipeId)

    if not recipes:
        Logger.fail("There was no recipe returned!")
    return jsonify(recipes)

@app.route('/recipe', methods=['PUT'])
@authorized
def addRecipe():
    recipe = Recipes(database)
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


@app.route('/recipe/<int:recipeId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editRecipe(recipeId, columnName, columnValue):
    recipe = Recipes(database)

    status, message = recipe.editRecipe(columnName, columnValue, recipeId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/step/<int:recipeId>', methods=['GET'])
def getStep(recipeId):
    step = Steps(database)

    steps = step.getStep(recipeId)

    if not steps:
        Logger.fail("There was no recipe returned!")
    return jsonify(steps)


@app.route('/recipe/step/<int:stepId>', methods=['DELETE'])
@authorized
def deleteStep(stepId):
    step = Steps(database)

    status, message = step.deleteStep(stepId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/step', methods=['PUT'])
@authorized
def addStep():
    step = Steps(database)

    params = request.get_json()

    recipeId = params.get('recipeId')
    photoId = params.get('photoId')
    stepNumber = params.get('stepNumber')
    stepDescription = params.get('stepDescription')

    status, message = step.addStep(recipeId, photoId, stepNumber,
                  stepDescription)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/step/<int:stepId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editStep(stepId, columnName, columnValue):
    step = Steps(database)

    status, message = step.editStep(columnName, columnValue, stepId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/ingredient/<int:recipeId>', methods=['GET'])
def getIngredient(recipeId):
    ingredient = Ingredients(database)

    ingredients = ingredient.getIngredient(recipeId)

    if not ingredients:
        Logger.fail("There was no ingredients returned!")
    return jsonify(ingredients)


@app.route('/recipe/ingredient/<int:ingredientId>', methods=['DELETE'])
@authorized
def deleteIngredient(ingredientId):
    ingredient = Ingredients(database)

    status, message = ingredient.deleteIngredient(ingredientId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/ingredient', methods=['PUT'])
@authorized
def addIngredient():
    ingredient = Ingredients(database)

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
    ingredient = Ingredients(database)

    status, message = ingredient.editIngredient(columnName, columnValue, ingredientId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/comment/<int:recipeId>', methods=['GET'])
def getComment(recipeId):
    comment = Comments(database)

    comments = comment.getComment(recipeId)

    if not comments:
        Logger.fail("There was no comments returned!")
    return jsonify(comments)


@app.route('/recipe/comment/<int:commentId>', methods=['DELETE'])
@authorized
def deleteComment(commentId):
    comment = Comments(database)

    status, message = comment.deleteComment(commentId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/comment', methods=['PUT'])
@authorized
def addCommentt():
    comment = Comments(database)

    params = request.get_json()

    recipeId = params.get('recipeId')
    comment = params.get('comment')

    status, message = comment.addComment(recipeId, get_user_id(), comment)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/comment/<int:commentId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editComment(commentId, columnName, columnValue):
    comment = Comments(database)

    status, message = comment.editComment(columnName, columnValue, commentId)

    return jsonify({
        'status': status,
        'message': message
    })

@app.route('/recipe/stars/<int:recipeId>', methods=['GET'])
def getStars(recipeId):
    star = Stars(database)
    userID = get_user_id()

    stars = star.getStars(recipeId, userID)

    if not stars:
        Logger.fail("There was no stars returned!")
    return jsonify(stars)


@app.route('/recipe/stars/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteStars(recipeId):
    star = Stars(database)
    userID = get_user_id()

    status, message = star.deleteStars(recipeId, userID)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/stars', methods=['PUT'])
@authorized
def addStars():
    star = Stars(database)
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


@app.route('/recipe/stars/<int:recipeId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def editStars(recipeId, columnName, columnValue):
    star = Stars(database)
    userID = get_user_id()

    status, message = star.editRecipe(columnName, columnValue, recipeId, userID)

    return jsonify({
        'status': status,
        'message': message
    })


def main():
    global database
    database = Database()
    if not database.connection(host=HOST, user=USER, password=PASSWORD, db=DATABASE):
        Logger.err("Database connection failed")
        return

    # This launches server
    app.secret_key = os.urandom(32)
    app.run(debug=True)


if __name__ == '__main__':
    main()
