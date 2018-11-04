from Logger import *
from utils import *


class Ingredients:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getIngredient(self, recipeID):
        getIngredientsQuery = u"SELECT recipe_id AS recipeId, ingredient_id AS ingredientId, " \
                u"ingredient_quantity AS ingredientQuantity, ingredient_unit AS ingredientUnit, " \
                u"ingredient_name AS ingredientName " \
                u"FROM ingredients "\
                u"WHERE recipe_id = {}; ".format(recipeID)

        getIngredientsQueryResult = self.database.query(getIngredientsQuery)

        getUserIdQuery = u"SELECT recipe_id AS recipeId, user_id AS userId " \
                    u"FROM recipes; "
        getUserIdQueryResult = self.database.query(getUserIdQuery)

        for getIngredientsQueryRow in getIngredientsQueryResult:
            for getUserIdQueryRow in getUserIdQueryResult:
                if getIngredientsQueryRow['recipeId'] == getUserIdQueryRow['recipeId']:
                    getIngredientsQueryRow['userId'] = getUserIdQueryRow['userId']

        if getIngredientsQueryResult:
            Logger.dbg(getIngredientsQueryResult)
            return getIngredientsQueryResult
        else:
            return {}

    def addIngredient(self, recipeId, ingredientQuantity, ingredientUnit,
                      ingredientName):

        if not checkIsInteger(ingredientQuantity):
            Logger.err("User has passed 'ingredientQuantity' as non-integer! '{}'".format(ingredientQuantity))
            return {}

        query = u"INSERT INTO ingredients " \
                u"(recipe_id, ingredient_quantity, ingredient_unit, ingredient_name) " \
                u"values ({}, {}, '{}', '{}' )".format(recipeId, int(ingredientQuantity), ingredientUnit, ingredientName)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            return 200, u'You added ingredients'
        else:
            return 404, u'Forwarded data are not correct'

    def editIngredient(self, columnName, columnValue, ingredientId):
        query = u"UPDATE ingredients " \
                u"SET {} = '{}'" \
                u"WHERE ingredient_id = {}".format(columnName, columnValue, ingredientId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteIngredient(self, ingredientId):
        query = u"DELETE FROM ingredients " \
                u"WHERE ingredient_id = {}".format(ingredientId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted ingredient_id = {}'.format(ingredientId)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteAllIngredient(self, recipeId):
        query = u"DELETE FROM ingredients " \
                u"WHERE recipe_id = {}".format(recipeId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'