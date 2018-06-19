from Database import *


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

        self.database.query(query)

        return 200, u'Przekazano login={}, password={}'.format(login, password)

