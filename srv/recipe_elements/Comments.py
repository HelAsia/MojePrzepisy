from Logger import *
import time


class Comments:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getComment(self, recipeID):
        query = u"SELECT C.recipe_id AS recipeId, U.user_login AS authorName, " \
                u"C.comment_id AS commentId, C.comment AS comment, C.created_date AS createdDate " \
                u"FROM comments AS C " \
                u"INNER JOIN users AS U " \
                u"ON C.user_id = U.user_id " \
                u"WHERE C.recipe_id = {}; ".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            for queryResultTime in queryResult:
                queryResultTimeCreatedDate = str(time.mktime(queryResultTime['createdDate'].timetuple()))
                queryResultTime['createdDate'] = queryResultTimeCreatedDate
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addComment(self, recipeId, userId, comment):
        query = u"INSERT INTO comments " \
                u"(recipe_id, user_id, comment, created_date) " \
                u"values ({}, {}, '{}', now() ".format(recipeId, userId, comment)

        queryResult, rows, msg = self.database.insert(query)

        queryCommentId = u"SELECT comment_id " \
                         u"FROM comments " \
                         u"WHERE " \
                         u"recipe_id = {} AND user_id = {} AND " \
                         u"comment = '{}' ".format(recipeId, userId, comment)

        queryCommentIdResult = self.database.query(queryCommentId)
        if queryResult:
            Logger.dbg(queryResult)
            return queryCommentIdResult
        else:
            return {}

    def editComment(self, columnName, columnValue, commentId):
        query = u"UPDATE comments " \
                u"SET {} = '{}'" \
                u"WHERE comment_id = {}".format(columnName, columnValue, commentId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteComment(self, commentId):
        query = u"DELETE FROM comments " \
                u"WHERE comment_id = {}".format(commentId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted comment_id = {}'.format(commentId)
        else:
            return 404, u'Forwarded data to check are not correct'
