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
        query = u'SELECT R.recipe_id AS recipeId, R.user_id AS userId, U.user_login AS authorName, ' \
                u'R.photo_id AS recipeMainPictureNumber, ' \
                u'R.recipe_name AS recipeName, ' \
                u'TIME_FORMAT(R.recipe_prepare_time, "%h %i %s") AS recipePrepareTime, ' \
                u'TIME_FORMAT(R.recipe_cook_time, "%h %i %s") AS recipeCookTime, ' \
                u'TIME_FORMAT(R.recipe_bake_time, "%h %i %s") AS recipeBakeTime, ' \
                u'R.recipe_category AS recipeCategory, R.recipe_created_date_time AS createdTime ' \
                u'FROM recipes AS R ' \
                u'INNER JOIN users AS U ' \
                u'ON R.user_id = U.user_id ' \
                u'WHERE R.recipe_id = {}; '.format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            queryResult[0]['recipePrepareTime'] = (str(queryResult[0]['recipePrepareTime'])).replace(' ', ':')
            queryResult[0]['recipeCookTime'] = (str(queryResult[0]['recipeCookTime'])).replace(' ', ':')
            queryResult[0]['recipeBakeTime'] = (str(queryResult[0]['recipeBakeTime'])).replace(' ', ':')
            return queryResult[0]
        else:
            return {}

    def addRecipe(self, userId, recipeName,
                  recipePrepareTime, recipeCookTime, recipeBakeTime,
                  recipeMainPicture, recipeCategory):

        photo = Photo(self.database)
        state, photoMsg = photo.addPhoto(recipeMainPicture)

        query = u"INSERT INTO recipes " \
                u"(user_id, recipe_name, recipe_prepare_time, recipe_cook_time, " \
                u"recipe_bake_time, photo_id, recipe_category, recipe_created_date_time) " \
                u"values ({}, '{}', '{}', '{}', '{}', '{}', " \
                u"'{}', NOW())".format(userId, recipeName, normalizeTime(recipePrepareTime),
                                        normalizeTime(recipeCookTime),normalizeTime(recipeBakeTime),
                                       int(photoMsg), recipeCategory)

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
        query = u"SELECT recipe_id, recipe_name, photo_id  " \
                u"FROM recipes " \
                u"WHERE recipe_id = {};".format(recipeId)

        isRecordInTable = self.database.query(query)

        Logger.dbg(isRecordInTable)

        if isRecordInTable:
            deleteRecipeQuery = u"DELETE FROM recipes " \
                    u"WHERE recipe_id = {};".format(recipeId)
            deleteRecipeQueryResult, rows, msg = self.database.delete(deleteRecipeQuery)

            deleteIngredientsQuery = u"DELETE FROM ingredients " \
                    u"WHERE recipe_id = {};".format(recipeId)
            deleteIngredientsQueryResult, rows, msg = self.database.delete(deleteIngredientsQuery)

            deleteStepsQuery = u"DELETE FROM steps " \
                    u"WHERE recipe_id = {};".format(recipeId)
            deleteStepsQueryResult, rows, msg = self.database.delete(deleteStepsQuery)

            deleteCommentsQuery = u"DELETE FROM comments " \
                    u"WHERE recipe_id = {};".format(recipeId)
            deleteCommentsQueryResult, rows, msg = self.database.delete(deleteCommentsQuery)

            deleteUserRecipesStarsQuery = u"DELETE FROM users_recipes_stars " \
                    u"WHERE recipe_id = {};".format(recipeId)
            deleteUserRecipesStarsQueryResult, rows, msg = self.database.delete(deleteUserRecipesStarsQuery)

            if deleteRecipeQueryResult and deleteIngredientsQueryResult \
                    and deleteStepsQueryResult and deleteCommentsQueryResult \
                    and deleteUserRecipesStarsQueryResult :
                Logger.dbg(deleteRecipeQueryResult)
                Logger.dbg(deleteIngredientsQueryResult)
                Logger.dbg(deleteCommentsQueryResult)
                Logger.dbg(deleteUserRecipesStarsQueryResult)
                return 200, u'Your deleted recipe_id = {}'.format(recipeId)
            else:
                return 404, u'Forwarded data to check are not correct'
        elif not isRecordInTable:
            return 404, u'Record is not exist in table'
