from Logger import *


class Users:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def loginUser(self,login, password):
        query = u"SELECT user_id " \
                u"FROM users " \
                u"WHERE user_login = '{}' AND user_password = '{}'".format(login, password)

        query_result = self.database.query(query)

        if query_result:
            Logger.dbg(query_result)
            return 200, u'Login={} and password={}'.format(login, password),query_result
        else:
            return 404, u'Login or password do not exist'

    def registerUser(self,login, password, firstName, lastName, email):
        query = u"INSERT INTO users " \
                u"(login, password, first_name, last_name, email) " \
                u"values '{}', '{}', '{}', '{}', '{}',".format(login, password, firstName, lastName, email)

        query_result = self.database.query(query)

        if query_result:
            Logger.dbg(str(tuple(query_result)))
            return 200, u'Registered login={},  password={}, first name={}, last name={}, email={}'.format(
                login, password, firstName, lastName, email
            )
        else:
            return 404, u'Data to register is not correct'

    def getUser(self,userID):
        query = u"SELECT login, first_name, second_name, email " \
                u"FROM users " \
                u"WHERE user_id = '{}'".format(userID)
        query_result = self.database.query(query)

        if query_result:
            Logger.dbg(str(tuple(query_result)))
            return 200, (guery_result)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteUser(self,login, password):
        query = u"DELETE FROM users " \
                u"WHERE user_login = '{}' AND user_password = '{}'".format(login, password)
        query_result = self.database.query(query)

        if query_result:
            Logger.dbg(str(tuple(query_result)))
            return 200, u'Your deleted login={},  password={}, first name={}, last name={}, email='.format(login, password, firstName, lastName, email)
        else:
            return 404, u'Forwarded data to check are not correct'
