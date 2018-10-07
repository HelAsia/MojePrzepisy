from Logger import *
import json
from decimal import Decimal as D
import re


class Cards:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getAllCards(self, userID):
        if not userID == -1:
            query = u"SELECT * FROM " \
                        u"(SELECT	R.recipe_id AS recipeId, R.recipe_name AS recipeName, " \
                        u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date, " \
                        u"U.user_login AS authorName, count(URS.favorite) AS favoritesCount, " \
                        u"ROUND(avg(URS.stars),0) AS starsCount " \
                        u"FROM recipes AS R " \
                        u"INNER JOIN users AS U " \
                        u"ON R.user_id = U.user_id " \
                        u"INNER JOIN users_recipes_stars AS URS " \
                        u"ON R.recipe_id = URS.recipe_id " \
                        u"GROUP BY R.recipe_id) R " \
                        u"LEFT OUTER JOIN " \
                        u"(SELECT favorite, recipe_id " \
                        u"FROM users_recipes_stars " \
                        u"WHERE user_id = {}) AS URSF " \
                        u"ON recipeId = URSF.recipe_id; ".format(userID)

        else:
            query = u"SELECT R.recipe_id AS recipeId, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            for queryRow in queryResult:
                if queryRow['favorite'] is None:
                    queryRow['favorite'] = False
                if queryRow['favorite'] == 0:
                     queryRow['favorite'] = False
                if queryRow['favorite'] == 1:
                    queryRow['favorite'] = True
                Logger.info(u"Zmienilo {}".format(queryRow['favorite']))
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}


    def getAllCardsSortedAlphabetically(self):
        query = u"SELECT R.recipe_id AS recipeId, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id "\
                u"ORDER BY R.recipe_name; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getAllCardsSortedByLastAdded(self):
        query = u"SELECT R.recipe_id AS recipeId, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id " \
                u"GROUP BY R.recipe_id " \
                u"ORDER BY R.date_time; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getAllCardsSortedByHighestRated(self):
        query = u"SELECT R.recipe_id AS recipeId, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id " \
                u"GROUP BY R.recipe_id " \
                u"ORDER BY ROUND(avg(URS.stars),0) DESC; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getSearchedCardsSortedByDefault(self, searchedQuery):
        query = u"SELECT R.recipe_id AS recipeId, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date " \
                u"FROM recipes AS R " \
                u"INNER JOIN users AS U " \
                u"ON R.user_id = U.user_id " \
                u"INNER JOIN users_recipes_stars AS URS " \
                u"ON R.recipe_id = URS.recipe_id " \
                u"WHERE R.recipe_name LIKE '%{}%'"\
                u"GROUP BY R.recipe_id; ".format(searchedQuery)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getAllCardsSortedByUser(self, userId):
        query = u"SELECT R.recipe_id AS recipeId, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as recipeMainPicture, R.recipe_created_date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id " \
                u"WHERE R.user_id LIKE '{}'" \
                u"GROUP BY R.recipe_id ".format(userId)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}