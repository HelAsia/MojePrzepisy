from Logger import *
import json
from decimal import Decimal as D
import re


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

#    def addRecipe(recipeName, recipeDescription,
#                  recipePrepareTime, recipeCookTime, recipeBakeTime,
#                  recipeMainPictureId, recipeCategory)