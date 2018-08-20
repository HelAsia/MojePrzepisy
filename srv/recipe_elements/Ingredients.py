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
                u"(recipe_id, ingredient_quantity, ingredient_unit, ingredient_name, group_ingredient_name) " \
                u"values ({}, '{}', '{}', {} ".format(recipeId, int(ingredientQuantity), ingredientUnit, ingredientName, ingredientGroup)

        queryResult = self.database.query(query)

        queryIngredientId = u"SELECT ingredient_id " \
                        u"FROM ingredients " \
                        u"WHERE " \
                        u"recipe_id = {} AND ingredient_quantity = {} AND ingredient_unit = '{}' AND " \
                        u"ingredient_name = '{}' AND group_ingredient_name = '{}' ".format(recipeId, ingredientQuantity, ingredientUnit, ingredientName, ingredientGroup)

        queryIngredientIdResult = self.database.query(queryIngredientId)
        if queryResult:
            Logger.dbg(queryResult)
            return queryIngredientIdResult
        else:
            return {}

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
