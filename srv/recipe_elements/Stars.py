from Logger import *
from utils import *


class Stars:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getStars(self, recipeID, userId):
        query = u"SELECT favorite AS favoritesCount, stars AS starsCount " \
                u"FROM users_recipes_stars " \
                u"WHERE recipe_id = {} AND user_id = {};".format(recipeID, userId)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getFavorite(self, recipeID, userId):
        query = u"SELECT favorite AS favorites " \
                u"FROM users_recipes_stars " \
                u"WHERE recipe_id LIKE {} AND user_id LIKE {}; ".format(recipeID, userId)

        queryResult = self.database.query(query)

        if not userId == -1:
            for queryRow in queryResult:
                if queryRow['favorites'] is None:
                    queryRow['favorites'] = False
                if queryRow['favorites'] == 0:
                    queryRow['favorites'] = False
                if queryRow['favorites'] == 1:
                    queryRow['favorites'] = True
            return queryResult[0]
        else:
            for queryRow in queryResult:
                queryRow['favorite'] = False

            if queryResult != None:
                return queryResult[0]
            return False

    def addStars(self, userID, recipeId, starsCount, favoritesCount):
        query = u"INSERT INTO users_recipes_stars " \
                u"(user_id, recipe_id, favorite, stars) " \
                u"values ({}, {}, {}, {} )" .format(userID, recipeId, favoritesCount, starsCount)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Stars has been added")
            return 200,  u'Forwarded data are correct'
        else:
            Logger.fail("NOT OK. Recipe hasn't been added")
            return 404, u'Forwarded data are not correct'

    def editStars(self, columnName, columnValue, recipeId, userID):
        isRecordInTable = self.getStars(recipeId,userID)

        Logger.dbg(isRecordInTable)

        if isRecordInTable != {}:
            if columnName == "favorite":
                if columnValue == 0:
                    columnValue = "false"
                elif columnValue == 1:
                    columnValue = "true"
            query = u"UPDATE users_recipes_stars " \
                    u"SET {} = {} " \
                    u"WHERE recipe_id = {} " \
                    u"AND user_id = {};".format(columnName, columnValue, recipeId, userID)

            queryResult, rows, msg = self.database.insert(query)
            Logger.dbg(queryResult)
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        elif isRecordInTable == {}:
            if columnName == "favorite":
                self.addStars(userID, recipeId, 0, columnValue)
            elif columnName == "stars":
                self.addStars(userID, recipeId, columnValue, False)
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteStars(self, recipeId, userID):
        query = u"DELETE FROM users_recipes_stars " \
                u"WHERE recipe_id = {}" \
                u"AND user_id = {};".format(recipeId, userID)

        queryResult = self.database.delete(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteAllStars(self, recipeId):
        query = u"DELETE FROM users_recipes_stars " \
                u"WHERE recipe_id = {};".format(recipeId)

        queryResult = self.database.delete(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'


    def getRecipeDetailsStars(self, userID, recipeID):
        recipeQuery = u"SELECT recipe_id AS recipeId, user_id AS userId " \
                      u"FROM recipes " \
                      u"WHERE recipe_id = {} ; ".format(int(recipeID))

        return self.getAllUpadtedStarsAndFavoritesBasedMethod(userID, recipeQuery)

    def getAllUpadtedStarsAndFavoritesBasedMethod(self, userID, recipeQuery):
        recipeQueryResult = self.database.query(recipeQuery)

        userQueryResult = self.getUserQueryResult()
        starsCountQueryResult = self.getStarsCountQueryResult()
        favoriteCountQueryResult = self.getFavoriteCountQueryResult()
        if not userID == -1:
            favoriteQueryResult = self.getFavoriteQueryResult(userID)
        else:
            favoriteQueryResult = []

        Logger.dbg(recipeQueryResult)
        mainQueryResult = list(recipeQueryResult[:])

        if mainQueryResult:
            mainQueryResultWithUser = self.addUserToMainQueryResult(mainQueryResult, userQueryResult)
            mainQueryResultWithUserAndStars = self.addStarsCountToMainQueryResult(mainQueryResultWithUser,
                                                                                  starsCountQueryResult)
            mainQueryResultWithUserAndStarsAndFavorite = self.addfavoriteCountToMainQueryResult(
                mainQueryResultWithUserAndStars, favoriteCountQueryResult)
            mainQueryResultWithUserAndStarsAndFavoriteByUser = self.addFavoriteToMainQueryResult(
                mainQueryResultWithUserAndStarsAndFavorite, favoriteQueryResult, userID)

            Logger.dbg(mainQueryResultWithUserAndStarsAndFavoriteByUser)
            return mainQueryResultWithUserAndStarsAndFavoriteByUser
        else:
            return []


    def getUserQueryResult(self):
        userQuery = u"SELECT user_id AS userId, user_login AS authorName " \
                    u"FROM users; "
        Logger.dbg("getUserQueryResult():")
        userQueryResult = self.database.query(userQuery)
        return userQueryResult


    def getStarsCountQueryResult(self):
        starsCountQuery = u"SELECT recipe_id AS recipeId, ROUND(avg(stars), 0) AS starsCount " \
                          u"FROM users_recipes_stars " \
                          u"GROUP BY recipeId; "
        Logger.dbg("getStarsCountQueryResult():")
        starsCountQueryResult = self.database.query(starsCountQuery)
        return starsCountQueryResult


    def getFavoriteCountQueryResult(self):
        favoriteCountQuery = u"SELECT recipe_id AS recipeId, count(favorite) AS favoritesCount " \
                             u"FROM users_recipes_stars " \
                             u"where favorite = true " \
                             u"GROUP BY recipeId; "
        Logger.dbg("getFavoriteCountQueryResult():")
        favoriteCountQueryResult = self.database.query(favoriteCountQuery)
        return favoriteCountQueryResult


    def getFavoriteQueryResult(self, userID):
        favoriteQuery = u"SELECT recipe_id AS recipeId, favorite AS favorites " \
                        u"FROM users_recipes_stars " \
                        u"where user_id = {}; ".format(userID)
        Logger.dbg("getFavoriteQueryResult():")
        favoriteQueryResult = self.database.query(favoriteQuery)
        return favoriteQueryResult


    def addUserToMainQueryResult(self, mainQueryResult, userQueryResult):
        for mainQueryRow in mainQueryResult:
            for userQueryRow in userQueryResult:
                if mainQueryRow['userId'] == userQueryRow['userId']:
                    mainQueryRow['authorName'] = userQueryRow['authorName']
        return mainQueryResult


    def addStarsCountToMainQueryResult(self, mainQueryResult, starsCountQueryResult):
        for mainQueryRow in mainQueryResult:
            for starsQueryRow in starsCountQueryResult:
                if mainQueryRow['recipeId'] == starsQueryRow['recipeId']:
                    mainQueryRow['starsCount'] = starsQueryRow['starsCount']
        return mainQueryResult


    def addfavoriteCountToMainQueryResult(self, mainQueryResult, favoriteCountQueryResult):
        for mainQueryRow in mainQueryResult:
            for favoriteCountQueryRow in favoriteCountQueryResult:
                if mainQueryRow['recipeId'] == favoriteCountQueryRow['recipeId']:
                    mainQueryRow['favoritesCount'] = favoriteCountQueryRow['favoritesCount']
        return mainQueryResult


    def addFavoriteToMainQueryResult(self, mainQueryResult, favoriteQueryResult, userID):
        if not userID == -1:
            for mainQueryRow in mainQueryResult:
                mainQueryRow['favorites'] = False
                for favoriteQueryRow in favoriteQueryResult:
                    if mainQueryRow['recipeId'] == favoriteQueryRow['recipeId']:
                        mainQueryRow['favorites'] = favoriteQueryRow['favorites']
                        if mainQueryRow['favorites'] is None:
                            mainQueryRow['favorites'] = False
                        if mainQueryRow['favorites'] == 0:
                            mainQueryRow['favorites'] = False
                        if mainQueryRow['favorites'] == 1:
                            mainQueryRow['favorites'] = True
            return mainQueryResult

        else:
            for mainQueryRow in mainQueryResult:
                mainQueryRow['favorites'] = False
            return mainQueryResult
