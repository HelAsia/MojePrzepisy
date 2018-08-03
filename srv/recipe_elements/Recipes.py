from Logger import *


class Recipes:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getRecipe(self, recipeID):
        query = u"SELECT R.recipe_id AS recipeID, U.user_login AS authorName, " \
                u"R.recipe_name AS recipeName, R.recipe_description AS recipeDescription, " \
                u"R.recipe_prepare_time AS prepareTime, R.recipe_cook_time AS cookTime, " \
                u"R.recipe_bake_time AS bakeTime, R.recipe_main_picture_id AS mainPictureId, " \
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

    def addRecipe(self, userId, recipeName, recipeDescription,
                  recipePrepareTime, recipeCookTime, recipeBakeTime,
                  recipeMainPictureId, recipeCategory):
        query = u"INSERT INTO recipes " \
                u"(user_id, recipe_name, recipe_description, recipe_prepare_time, recipe_cook_time, " \
                u"recipe_bake_time, recipe_main_picture_id, recipe_category, recipe_created_date_time) " \
                u"values ({}, '{}', '{}', {}, {}, {}, '{}', " \
                u"{}, NOW())".format(userId, recipeName, recipeDescription, recipePrepareTime, recipeCookTime,
                                recipeBakeTime, recipeMainPictureId, recipeCategory)

        queryResult = self.database.query(query)

        queryRecipeId = u"SELECT recipe_id "\
                        u"FROM recipes "\
                        u"WHERE "\
                        u"user_id = {} AND recipe_name = '{}' AND recipe_description = '{}' AND "\
                        u"recipe_prepare_time = {} AND recipe_cook_time = {} AND recipe_bake_time = {} "\
                        u"AND recipe_main_picture_id = {} " \
                        u"AND recipe_category = '{}' AND ".format(userId, recipeName, recipeDescription,
                                                                  recipePrepareTime, recipeCookTime,
                                                                  recipeBakeTime, recipeMainPictureId, recipeCategory)

        queryRecipeIdResult = self.database.query(queryRecipeId)
        if queryResult:
            Logger.dbg(queryResult)
            return queryRecipeIdResult
        else:
            return {}

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
