from Logger import *
from Photo import *


class Steps:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getStep(self, recipeID):
        query = u"SELECT recipe_id AS recipeId, step_id AS stepId, " \
                u"photo_id AS photoNumber, step_number AS stepNumber, " \
                u"step_description AS stepDescription " \
                u"FROM steps "\
                u"WHERE recipe_id = {}; ".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addStep(self, recipeId, photoImage, stepNumber,
                stepDescription):

        photo = Photo(self.database)
        state, PhotoMsg = photo.addPhoto(photoImage)

        query = u"INSERT INTO steps " \
                u"(recipe_id, photo_id, step_number, step_description) " \
                u"values ({}, '{}', {}, '{}' )".format(recipeId, int(PhotoMsg), int(stepNumber), stepDescription)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Step has been added")
            return 200, u'You added steps'
        else:
            Logger.fail("NOT OK. Step hasn't been added")
            return 404, u'Forwarded data are not correct'

    def editStep(self, columnName, columnValue, stepId):
        query = u"UPDATE steps " \
                u"SET {} = '{}'" \
                u"WHERE step_id = {}".format(columnName, columnValue, stepId)
        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteStep(self, stepId):
        query = u"DELETE FROM steps " \
                u"WHERE step_id = {}".format(stepId)
        queryResult, rows, msg = self.database.delete(query)

        if queryResult:
            Logger.dbg(queryResult)
            return 200, u'Your deleted step_id = {}'.format(stepId)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteAllStep(self, recipeId):
        query = u"DELETE FROM steps " \
                u"WHERE recipe_id = {}".format(recipeId)
        queryResult, rows, msg = self.database.delete(query)

        if queryResult:
            Logger.dbg(queryResult)
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'
