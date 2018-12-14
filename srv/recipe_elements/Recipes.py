from Logger import *
from utils import *
from Photo import *
import datetime


class Recipes:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getRecipe(self, recipeID):
        query = u'SELECT R.recipe_id AS id, R.user_id AS userId, U.user_login AS authorName, ' \
                u'R.photo_id AS mainPictureNumber, ' \
                u'R.recipe_name AS name, ' \
                u'TIME_FORMAT(R.recipe_prepare_time, "%h %i %s") AS prepareTime, ' \
                u'TIME_FORMAT(R.recipe_cook_time, "%h %i %s") AS cookTime, ' \
                u'TIME_FORMAT(R.recipe_bake_time, "%h %i %s") AS bakeTime, ' \
                u'R.recipe_category AS category, R.recipe_created_date_time AS createdTime ' \
                u'FROM recipes AS R ' \
                u'INNER JOIN users AS U ' \
                u'ON R.user_id = U.user_id ' \
                u'WHERE R.recipe_id = {}; '.format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            queryResult[0]['prepareTime'] = (str(queryResult[0]['prepareTime'])).replace(' ', ':')
            queryResult[0]['cookTime'] = (str(queryResult[0]['cookTime'])).replace(' ', ':')
            queryResult[0]['bakeTime'] = (str(queryResult[0]['bakeTime'])).replace(' ', ':')
            return queryResult[0]
        else:
            return {}

    def addRecipe(self, userId, recipeList):

        name = recipeList['name']
        prepareTime = recipeList['prepareTime']
        cookTime = recipeList['cookTime']
        bakeTime = recipeList['bakeTime']
        category = recipeList['category']
        mainPictureNumber = recipeList['mainPictureNumber']

        query = u"INSERT INTO recipes " \
                u"(user_id, recipe_name, recipe_prepare_time, recipe_cook_time, " \
                u"recipe_bake_time, photo_id, recipe_category, recipe_created_date_time) " \
                u"values ({}, '{}', '{}', '{}', '{}', '{}', " \
                u"'{}', NOW())".format(userId, name, normalizeTime(prepareTime),
                                        normalizeTime(cookTime),normalizeTime(bakeTime),
                                       int(mainPictureNumber), category)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Recipe has been added")
            queryRecipeId = u"SELECT MAX(recipe_id) AS id FROM recipes; "
            recipe_id = self.database.query(queryRecipeId)
            if recipe_id and len(recipe_id) >= 1:
                message = recipe_id[0]['id']
                return 200, unicode(message)
            else:
                message = 'Could not return id'
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

    def deleteRecipe(self, id):
        query = u"SELECT recipe_id, recipe_name, photo_id  " \
                u"FROM recipes " \
                u"WHERE recipe_id = {};".format(id)

        isRecordInTable = self.database.query(query)

        Logger.dbg(isRecordInTable)

        if isRecordInTable:
            deleteRecipeQuery = u"DELETE FROM recipes " \
                    u"WHERE recipe_id = {};".format(id)
            deleteRecipeQueryResult, rows, msg = self.database.delete(deleteRecipeQuery)

            deleteIngredientsQuery = u"DELETE FROM ingredients " \
                    u"WHERE recipe_id = {};".format(id)
            deleteIngredientsQueryResult, rows, msg = self.database.delete(deleteIngredientsQuery)

            deleteStepsQuery = u"DELETE FROM steps " \
                    u"WHERE recipe_id = {};".format(id)
            deleteStepsQueryResult, rows, msg = self.database.delete(deleteStepsQuery)

            deleteCommentsQuery = u"DELETE FROM comments " \
                    u"WHERE recipe_id = {};".format(id)
            deleteCommentsQueryResult, rows, msg = self.database.delete(deleteCommentsQuery)

            deleteUserRecipesStarsQuery = u"DELETE FROM users_recipes_stars " \
                    u"WHERE recipe_id = {};".format(id)
            deleteUserRecipesStarsQueryResult, rows, msg = self.database.delete(deleteUserRecipesStarsQuery)

            if deleteRecipeQueryResult and deleteIngredientsQueryResult \
                    and deleteStepsQueryResult and deleteCommentsQueryResult \
                    and deleteUserRecipesStarsQueryResult :
                Logger.dbg(deleteRecipeQueryResult)
                Logger.dbg(deleteIngredientsQueryResult)
                Logger.dbg(deleteCommentsQueryResult)
                Logger.dbg(deleteUserRecipesStarsQueryResult)
                return 200, u'Your deleted recipe_id = {}'.format(id)
            else:
                return 404, u'Forwarded data to check are not correct'
        elif not isRecordInTable:
            return 404, u'Record is not exist in table'
