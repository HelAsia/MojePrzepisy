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
                u"P.photo_url AS photoURL, P.photo_image AS photoImage, S.step_number AS stepNumber, " \
                u"S.step_description AS stepDescription " \
                u"FROM steps AS S " \
                u"INNER JOIN photos AS P " \
                u"ON S.photo_id = P.photo_id " \
                u"WHERE R.recipe_id LIKE '{}'".format(recipeID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addStep(self, recipeId, photoId, stepNumner,
                  stepDescription,):
        query = u"INSERT INTO steps " \
                u"(recipe_id, photo_id, step_number, step_description) " \
                u"values ({}, '{}', '{}', {} ".format(recipeId, photoId, stepNumner, stepDescription)

        queryResult = self.database.query(query)

        queryStepId = u"SELECT step_id " \
                        u"FROM steps " \
                        u"WHERE " \
                        u"recipe_id = {} AND photo_id = {} AND step_number = {} AND " \
                        u"step_description = '{}' ".format(recipeId, photoId, stepNumner, stepDescription)

        queryStepIdResult = self.database.query(queryStepId)
        if queryResult:
            Logger.dbg(queryResult)
            return queryStepIdResult
        else:
            return {}

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
