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

    def getAllCards(self):
        query = u"SELECT R.recipe_id AS id, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}


    def getAllCardsSortedAlphabetically(self):
        query = u"SELECT R.recipe_id AS id, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id " \
                u"ORDER BY R.recipe_name; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getAllCardsSortedByLastAdded(self):
        query = u"SELECT R.recipe_id AS id, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id " \
                u"ORDER BY R.date_time; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getAllCardsSortedByHighestRated(self):
        query = u"SELECT R.recipe_id AS id,, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id " \
                u"ORDER BY ROUND(avg(URS.stars),0) DESC; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getSearchedCardsSortedByDefault(self, searchedQuery):
        query = u"SELECT R.recipe_id AS id, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date " \
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
        query = u"SELECT R.recipe_id AS id,, R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
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