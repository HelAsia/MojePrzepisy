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
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, "\
        u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date "\
        u"FROM recipes; "

        userQuery = u"SELECT user_id AS userId, user_login AS authorName "\
        u"FROM users; "

        starsCountQuery = u"SELECT recipe_id AS recipeId, ROUND(avg(stars), 0) AS starsCount "\
        u"FROM users_recipes_stars "\
        u"WHERE stars > 0 "\
        u"GROUP BY recipeId; "

        favoriteCountQuery = u"SELECT recipe_id AS recipeId, count(favorite) AS favoritesCount "\
        u"FROM users_recipes_stars "\
        u"where favorite = true "\
        u"GROUP BY recipeId; "

        favoriteQuery = u"SELECT recipe_id AS recipeId, favorite "\
        u"FROM users_recipes_stars "\
        u"where user_id = {}; ".format(userID)

        recipeQueryResult = self.database.query(recipeQuery)
        userQueryResult = self.database.query(userQuery)
        starsCountQueryResult = self.database.query(starsCountQuery)
        favoriteCountQueryResult = self.database.query(favoriteCountQuery)
        favoriteQueryResult = self.database.query(favoriteQuery)

        mainQueryResult = recipeQueryResult

        if mainQueryResult:
            for mainQueryRow in mainQueryResult:
                for userQueryRow in userQueryResult:
                    if mainQueryRow['userId'] == userQueryRow['userId']:
                        mainQueryRow['authorName'] = userQueryRow['authorName']

            for mainQueryRow in mainQueryResult:
                for starsQueryRow in starsCountQueryResult:
                    if mainQueryRow['recipeId'] == starsQueryRow['recipeId']:
                        mainQueryRow['starsCount'] = starsQueryRow['starsCount']

            for mainQueryRow in mainQueryResult:
                for favoriteCountQueryRow in favoriteCountQueryResult:
                    if mainQueryRow['recipeId'] == favoriteCountQueryRow['recipeId']:
                        mainQueryRow['favoritesCount'] = favoriteCountQueryRow['favoritesCount']

            if not userID == -1:
                for mainQueryRow in mainQueryResult:
                    mainQueryRow['favorite'] = False
                    for favoriteQueryRow in favoriteQueryResult:
                        if mainQueryRow['recipeId'] == favoriteQueryRow['recipeId']:
                            mainQueryRow['favorite'] = favoriteQueryRow['favorite']
                            if mainQueryRow['favorite'] is None:
                                mainQueryRow['favorite'] = False
                            if mainQueryRow['favorite'] == 0:
                                mainQueryRow['favorite'] = False
                            if mainQueryRow['favorite'] == 1:
                                mainQueryRow['favorite'] = True
            else:
                for mainQueryRow in mainQueryResult:
                    mainQueryRow['favorite'] = False

            Logger.dbg(mainQueryResult)
            return mainQueryResult
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