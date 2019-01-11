from Logger import *
import time
import datetime


class Comments:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getComments(self, recipeID):
        query = u"SELECT C.recipe_id AS recipeId, U.user_login AS authorName, U.user_id AS userId, " \
                u"C.comment_id AS id, C.comment AS comment, C.created_date AS createdDate " \
                u"FROM comments AS C " \
                u"INNER JOIN users AS U " \
                u"ON C.user_id = U.user_id " \
                u"WHERE C.recipe_id = {}; ".format(recipeID)

        queryResult = self.database.query(query)

        f = '%Y-%m-%d %H:%M:%S'

        if queryResult:
            for queryResultTime in queryResult:
                queryResultTime['createdDate'] = queryResultTime['createdDate'].strftime(f)

            Logger.dbg(queryResult)
            return queryResult
        else:
            Logger.dbg(queryResult)
            return {}

    def addComment(self, recipeId, userId, comment):
        query = u"INSERT INTO comments " \
                u"(recipe_id, user_id, comment, created_date) " \
                u"values ({}, {}, '{}', now());".format(recipeId, userId, comment)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Comment has been added")
            queryCommentId = u"SELECT MAX(comment_id) AS id FROM comments; "
            comment_id = self.database.query(queryCommentId)
            if comment_id and len(comment_id) >= 1:
                message = comment_id[0]['id']
                return 200, unicode(message)
            else:
                message = 'Could not return id'
                return 404, unicode(message)
        else:
            Logger.fail("NOT OK. Comment hasn't been added")
            return 404, u'Forwarded data are not correct'

    def editComment(self, columnValue, id):
        query = u"UPDATE comments " \
                u"SET comment = '{}', created_date = now() " \
                u"WHERE comment_id = {};".format(columnValue, id)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteComment(self, id):
        query = u"SELECT comment_id, comment " \
                u"FROM comments " \
                u"WHERE comment_id = {};".format(id)

        isRecordInTable = self.database.query(query)

        Logger.dbg(isRecordInTable)

        if isRecordInTable:
            query = u"DELETE FROM comments " \
                    u"WHERE comment_id = {};".format(id)
            queryResult, rows, msg = self.database.delete(query)

            if queryResult:
                Logger.dbg(queryResult)
                return 200, u'Your deleted comment_id = {}'.format(id)
            else:
                return 404, u'Forwarded data to check are not correct'
        elif not isRecordInTable:
            return 404, u'Record is not exist in table'

    def deleteAllComment(self, recipeId):
        query = u"DELETE FROM comments " \
                u"WHERE recipe_id = {}".format(recipeId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'

