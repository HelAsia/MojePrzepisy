from Logger import *
from recipe_elements.Photo import *
import json
from decimal import Decimal as D
import re


class Users:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database


    def setDatabase(self,database):
        self.database = database


    def loginUser(self,login, password):
        if len(login) >= 80:
            return 500, u'Passed login is too long', None

        if len(password) >= 80:
            return 500, u'Passed login is too long', None

        if len(login) == 0:
            return 500, u'Passed login was empty', None

        query = u"SELECT user_id AS userId " \
                u"FROM users " \
                u"WHERE user_login = '{}' AND user_password = '{}'".format(login, password)

        queryResult = self.database.query(query)

        if queryResult :
            return 200, u'You are logged in', queryResult[0]['userId']
        else:
            return 404, u'Login or password do not exist', None


    def registerUser(self,login, password, firstName, lastName, email):
        regexPassword = r"^(?=.*\d)(?=.*[a-z])(?=.*[\!\@\#\$\%\^\&\*\(\)\_\+\-\=])(?=.*[A-Z])(?!.*\s).{8,}$"
        regexEmail = r"^[-\w\.]+@([-\w]+\.)+[a-z]+$"

        matchesPassword = re.search(regexPassword,password)
        matchesEmail = re.search(regexEmail, email)

        if matchesPassword:
            if matchesEmail:
                query = u"INSERT INTO users " \
                        u"(user_login, user_password, first_name, last_name, email) " \
                        u"values ('{}', '{}', '{}', '{}', '{}')".format(login, password, firstName, lastName, email)

                status, queryResult, errorMessage = self.database.insert(query)

                if status:
                    queryUserId = u"SELECT MAX(user_id) AS userId FROM users; "
                    user_id = self.database.query(queryUserId)
                    return 200, user_id[0]['userId']
                elif not status:
                    if queryResult == 1062:
                        return 404, u'This login exists in Database'
                    else:
                        return 500, errorMessage
            else:
                return 500, u'Wrong type of email'
        else:
            return 500, u'Wrong type of password'


    def getUser(self, userID):
        query = u"SELECT user_login AS login, user_password AS password, first_name AS firstName, last_name AS lastName, email, photo_id AS photoId " \
                u"FROM users " \
                u"WHERE user_id = '{}'".format(userID)
        queryResult = self.database.query(query)

        if queryResult:
            return queryResult[0]
        else:
            return {'userID': "-1"}


    def deleteUser(self, userID):
        query = u"DELETE FROM users " \
                u"WHERE user_id = {}".format(userID)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted user_id = {}'.format(userID)
        else:
            return 404, u'Forwarded data to check are not correct'


    def editUser(self, columnName, columnValue, userID):
        if columnName == 'photo_id':
            photo = Photo(self.database)
            state, photoMsg = photo.addPhoto(columnValue)

            query = u"UPDATE users " \
                    u"SET {} = '{}'" \
                    u"WHERE user_id = {}".format(columnName, photoMsg, userID)
            queryResult, rows, msg = self.database.insert(query)

        else:
            query = u"UPDATE users " \
                    u"SET {} = '{}'" \
                    u"WHERE user_id = {}".format(columnName, columnValue, userID)
            queryResult, rows, msg = self.database.insert(query)

        if rows > 0:
            Logger.dbg(queryResult)
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            Logger.dbg(queryResult)
            return 404, u'Forwarded data to check are not correct'

    def editUserPhoto(self, photoString, userID):
        photo = Photo(self.database)
        state, photoMsg = photo.addPhoto(photoString)

        query = u"UPDATE users " \
                u"SET photo_id = '{}'" \
                u"WHERE user_id = {}".format(photoMsg, userID)
        queryResult, rows, msg = self.database.insert(query)

        if rows > 0:
            Logger.dbg(queryResult)
            return 200, u'Your changed photo_id = {}'.format(photoMsg)
        else:
            Logger.dbg(queryResult)
            return 404, u'Forwarded data to check are not correct'

