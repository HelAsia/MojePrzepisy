import MySQLdb
import MySQLdb.cursors
import MySQLdb.converters

from Logger import *
import datetime


class Database:
    databaseConnection = None
    databaseCursor = None

    def __init__(self):
        pass

    def connection(self, host, user, password, db):
        try:
            conv = MySQLdb.converters.conversions.copy()
            conv[246] = float
            conv[0] = float

            if password:
                self.databaseConnection = MySQLdb.connect(
                    host=host,
                    user=user,
                    passwd=password,
                    db=db,
                    cursorclass=MySQLdb.cursors.DictCursor,
                    conv = conv
                )
            else:
                self.databaseConnection = MySQLdb.connect(
                    host=host,
                    user=user,
                    db=db,
                    cursorclass=MySQLdb.cursors.DictCursor,
                    conv=conv
                )

            self.databaseConnection.set_character_set('utf8')

            Logger.info("Database connection succeeded.")

            self.databaseCursor = self.databaseConnection.cursor()
            self.databaseCursor.execute('SET NAMES utf8;')
            self.databaseCursor.execute('SET CHARACTER SET utf8;')
            self.databaseCursor.execute('SET character_set_connection=utf8;')

            return True

        except (MySQLdb.Error, MySQLdb.Error) as e:
            Logger.err("Database connection failed: " + str(e))
            return False

    def query(self, query):
        Logger.dbg(u'SQL query: "{}"'.format(query))

        try:
            self.databaseCursor.execute(query)
            result = self.databaseCursor.fetchall()

            num = 0
            for row in result:
                num += 1
                if num > 5: break
                Logger.dbg(u'Row {}.: '.format(num) + str(row))

            return result

        except (MySQLdb.Error, MySQLdb.Error) as e:
            Logger.err(e)

            return False

    def insert(self, query):
        '''
            Executes SQL query that is an INSERT statement.

        params:
            query 	SQL INSERT query

        returns:
                (boolean Status, int AffectedRows, string Message)

            Where:
                Status          - false on Error, true otherwise
                AffectedRows    - number of affected rows or error code on failure
                Message         - error message on failure, None otherwise
        '''

        Logger.dbg(u'SQL query: "{}"'.format(query))

        try:
            res = self.databaseCursor.execute(query)

            # Commit new records to the database
            result = self.databaseConnection.commit()
            return True, res, None

        except (MySQLdb.Error, MySQLdb.Error) as e:
            Logger.err(e)

            # Rollback introduced changes
            self.databaseConnection.rollback()
            return False, e.args[0], e.args[1]

    def delete(self, query):
        return self.insert(query)