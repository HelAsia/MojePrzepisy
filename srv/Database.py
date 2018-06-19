import MySQLdb

from Logger import *


class Database:
    databaseConnection = None
    databaseCursor = None

    def __init__(self):
        pass

    def connection(self, host, user, password, db):
        try:
            self.databaseConnection = MySQLdb.connect(host=host,
                                                      user = user,
                                                      password=password,
                                                      db=db)

            self.databaseConnection.set_character_set('utf8')

            Logger.info("Database connection succeeded.")

            self.databaseCursor = self.databaseConnection.cursor()
            self.databaseCursor.execute('SET NAMES utf8;')
            self.databaseCursor.execute('SET CHARACTER SET utf8;')
            self.databaseCursor.execute('SET character_set_connection=utf8;')

            return True

        except (MySQLdb.Error, MySQLdb.Error) as e:
            Logger.err(e)
            return False

    def query(self,query):
        Logger.dbg(query)
        try:
            self.databaseCursor.execute(query)
            result = self.databaseCursor.fetchall()

            for row in result:
                Logger.dbg(row[0])

            return result

        except (MySQLdb.Error, MySQLdb.Error) as e:
            Logger.err(e)

            return False