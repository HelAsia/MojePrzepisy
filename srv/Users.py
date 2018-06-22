from Logger import *


class Users:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def login(self,login, password):
        query = u"SELECT user_id " \
                u"FROM users " \
                u"WHERE user_login = '{}' AND user_password = '{}'".format(login, password)

        query_result = self.database.query(query)
        if query_result:
            Logger.dbg(str(tuple(query_result)))
            return 200, u'Forwarded login={} and password={}'.format(login, password)
        else:
            return 404, u'Forwarded login or password do not exist'

