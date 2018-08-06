from Logger import *


class Photos:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getPhoto(self, photoId):
        query = u"SELECT P.photo_id_id AS photoId, P.photo_url AS photoURL, " \
                u"P.photo_image AS photoImage " \
                u"FROM photos AS P " \
                u"WHERE I.photo_id LIKE '{}'".format(photoId)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addPhoto(self, photoURL, photoImage):
        query = u"INSERT INTO photos " \
                u"(photo_url, photo_image) " \
                u"values ('{}', {} ".format(photoURL, photoImage)

        queryResult = self.database.query(query)

        queryPhotoId = u"SELECT photo_id " \
                            u"FROM photos " \
                            u"WHERE " \
                            u"photo_url = '{}' AND photo_image = '{}' ".format(photoURL, photoImage)

        queryPhotoIdResult = self.database.query(queryPhotoId)
        if queryResult:
            Logger.dbg(queryResult)
            return queryPhotoIdResult
        else:
            return {}

    def editPhoto(self, columnName, columnValue, photoId):
        query = u"UPDATE photos " \
                u"SET {} = '{}'" \
                u"WHERE photo_id = {}".format(columnName, columnValue, photoId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deletePhoto(self, photoId):
        query = u"DELETE FROM photos " \
                u"WHERE photo_id = {}".format(photoId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted photo_id = {}'.format(photoId)
        else:
            return 404, u'Forwarded data to check are not correct'
