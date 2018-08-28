from Logger import *


class Steps:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getStep(self, recipeID):
        query = u"SELECT S.recipe_id AS id, S.step_id AS stepId " \
                u"S.photo_image AS photoImage, S.step_number AS stepNumber, " \
                u"S.step_description AS stepDescription " \
                u"FROM steps AS S " \
                u"WHERE S.recipe_id LIKE '{}'".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addStep(self, recipeId, photoImage, stepNumber,
                stepDescription):
        query = u"INSERT INTO steps " \
                u"(recipe_id, photo_image, step_number, step_description) " \
                u"values ({}, '{}', '{}', {} ".format(recipeId, photoImage, stepNumber, stepDescription)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return 200, u'You added steps'
        else:
            return 404, u'Forwarded data are not correct'

    def editStep(self, columnName, columnValue, stepId):
        query = u"UPDATE steps " \
                u"SET {} = '{}'" \
                u"WHERE step_id = {}".format(columnName, columnValue, stepId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteStep(self, stepId):
        query = u"DELETE FROM steps " \
                u"WHERE step_id = {}".format(stepId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted step_id = {}'.format(stepId)
        else:
            return 404, u'Forwarded data to check are not correct'
