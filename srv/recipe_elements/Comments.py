from Logger import *


class Comments:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getComment(self, recipeID):
        query = u"SELECT I.recipe_id AS recipeID, U.user_login AS authorName, " \
                u"I.comment_id AS commentId, I.comment AS comment, I.created_date AS createdDate, " \
                u"FROM ingredients AS I " \
                u"INNER JOIN users AS U " \
                u"ON I.user_id = U.user_id " \
                u"WHERE I.recipe_id LIKE '{}'".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addComment(self, recipeId, userId, comment,
                   createdDate):
        query = u"INSERT INTO comments " \
                u"(recipe_id, user_id, comment, created_date) " \
                u"values ({}, {}, '{}', now() ".format(recipeId, userId, comment, createdDate)

        queryResult = self.database.query(query)

        queryCommentId = u"SELECT comment_id " \
                            u"FROM comments " \
                            u"WHERE " \
                            u"recipe_id = {} AND user_id = {} AND comment = '{}' AND " \
                            u"created_date = {} ".format(recipeId, userId, comment, createdDate)

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
