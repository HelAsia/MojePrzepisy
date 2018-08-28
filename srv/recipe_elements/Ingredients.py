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
        query = u"SELECT I.recipe_id AS id, I.ingredient_id AS ingredientId " \
                u"I.ingredient_quantity AS ingredientQuantity, I.ingredient_unit AS ingredientUnit, " \
                u"I.ingredient_name AS ingredientName " \
                u"FROM ingredient AS I " \
                u"WHERE R.recipe_id LIKE '{}'".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addIngredient(self, recipeId, ingredientQuantity, ingredientUnit,
                      ingredientName, ingredientGroup):

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
