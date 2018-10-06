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
                u"WHERE recipe_id LIKE '{}' AND user_id LIKE '{}')".format(recipeID, userId)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def getRecipeDetailsStars(self, recipeID):
        query = u"SELECT count(favorite) AS favoritesCount, ROUND(avg(stars),0) AS starsCount " \
                u"FROM users_recipes_stars " \
                u"WHERE recipe_id = {}; ".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult[0]
        else:
            return {}

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
        if columnName == "favorite":
            if columnValue == 0:
                columnValue = "false"
            else:
                columnValue = "true"
        query = u"UPDATE users_recipes_stars " \
                u"SET {} = {} " \
                u"WHERE recipe_id = {} " \
                u"AND user_id = {}".format(columnName, columnValue, recipeId, userID)

        queryResult, rows, msg = self.database.insert(query)

        if rows >= 0:
            Logger.dbg(queryResult)
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
 #       elif rows == 0:
 #           Logger.dbg(queryResult)
 #           self.addStars(userID, recipeId, columnValue, 0)
 #           return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            Logger.dbg(queryResult)
            return 404, u'Forwarded data to check are not correct'

    def deleteStars(self, recipeId, userID):
        query = u"DELETE FROM users_recipes_stars " \
                u"WHERE recipe_id = {}" \
                u"AND user_id = {})".format(recipeId, userID)

        queryResult = self.database.delete(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'
