from Logger import *
from utils import *


class Recipes:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getRecipeId(self, userID, recipeName, recipePrepareTime,
                    recipeCookTime, recipeBakeTime, recipeCategory):
        queryRecipeId = u"SELECT recipe_id "\
                        u"FROM recipes "\
                        u"WHERE "\
                        u"user_id = {} AND recipe_name = '{}' AND "\
                        u"recipe_prepare_time = '{}' AND recipe_cook_time = '{}' AND recipe_bake_time = '{}' "\
                        u"AND recipe_category = '{}' ".format(userID, recipeName, normalizeTime(recipePrepareTime),
                                                              normalizeTime(recipeCookTime),normalizeTime(recipeBakeTime),
                                                              recipeCategory)

        queryResult = self.database.query(queryRecipeId)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getRecipe(self, recipeID):
        query = u"SELECT R.recipe_id AS recipeId, U.user_login AS authorName, " \
                u"R.recipe_main_picture AS recipeMainPicture" \
                u"R.recipe_name AS recipeName, " \
                u"R.recipe_prepare_time AS prepareTime, R.recipe_cook_time AS cookTime, " \
                u"R.recipe_bake_time AS bakeTime, R.recipe_main_picture AS recipeMainPicture, " \
                u"R.recipe_category AS category, R.recipe_created_date_time AS createdTime" \
                u"FROM recipes AS R " \
                u"INNER JOIN users AS U " \
                u"ON R.user_id = U.user_id " \
                u"WHERE R.recipe_id LIKE '{}'".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addRecipe(self, userId, recipeName,
                  recipePrepareTime, recipeCookTime, recipeBakeTime,
                  recipeMainPictureId, recipeCategory):

        query = u"INSERT INTO recipes " \
                u"(user_id, recipe_name, recipe_prepare_time, recipe_cook_time, " \
                u"recipe_bake_time, recipe_main_picture, recipe_category, recipe_created_date_time) " \
                u"values ({}, '{}', '{}', '{}', '{}', '{}', " \
                u"'{}', NOW())".format(userId, recipeName, normalizeTime(recipePrepareTime),
                                        normalizeTime(recipeCookTime),normalizeTime(recipeBakeTime),
                                       recipeMainPictureId, recipeCategory)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Recipe has been added")
            queryRecipeId = u"SELECT MAX(recipe_id) AS recipeId FROM recipes; "
            recipe_id = self.database.query(queryRecipeId)
            if recipe_id and len(recipe_id) >= 1:
                message = recipe_id[0]['recipeId']
                return 200, unicode(message)
            else:
                message = 'Could not return recipeId'
                return 404, unicode(message)
        else:
            Logger.fail("NOT OK. Recipe hasn't been added")
            return 404, u'Forwarded data are not correct'

    def editRecipe(self, columnName, columnValue, recipeId):
        query = u"UPDATE recipes " \
                u"SET {} = '{}'" \
                u"WHERE recipe_id = {}".format(columnName, columnValue, recipeId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteRecipe(self, recipeId):
        query = u"DELETE FROM recipes " \
                u"WHERE recipe_id = {}".format(recipeId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'
