from Logger import *
from utils import *
import base64


class Photo:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getPhoto(self, photoId):
        if not checkIsInteger(photoId):
            return 500

        query = u"SELECT photo_id AS photoId, photo " \
                u"FROM photos " \
                u"WHERE photo_id = {}; ".format(int(photoId))

        queryResult = self.database.query(query)
        photoList = list(queryResult[:])
        photoString = photoList[0]['photo']

        if queryResult:
            return photoString.decode('base64')
        else:
            return ''

    def addPhoto(self, photoString):

        query = u"INSERT INTO photos " \
                u"(photo) " \
                u"values ('{}' )".format(photoString)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Photo has been added")
            queryPhotoId = u"SELECT MAX(photo_id) AS photoId FROM photos; "
            photo_id = self.database.query(queryPhotoId)
            if photo_id and len(photo_id) >= 1:
                message = photo_id[0]['photoId']
                return 200, unicode(message)
            else:
                message = 'Could not return photoId'
                return 404, unicode(message)
        else:
            Logger.fail("NOT OK. Photo hasn't been added")
            return 404, u'Forwarded data are not correct'

    def editPhoto(self, photoId, photoString):
        query = u"UPDATE photos " \
                u"SET photo = '{}'" \
                u"WHERE photo_id = {}".format(photoString, photoId)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'You changed photo'
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