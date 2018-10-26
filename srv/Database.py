import MySQLdb
import MySQLdb.cursors
import MySQLdb.converters

from Logger import *
import datetime


class Database:
    databaseConnection = None
    databaseCursor = None
    lastUsedCredentials = {
        'host': '',
        'user': '',
        'password': '',
        'db': ''
    }

    def __init__(self):
        pass

    def __del__(self):
        Logger.dbg("Closing database connection.")
        self.databaseConnection.close()
        self.databaseConnection = None

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

            self.lastUsedCredentials.update({
                'host': host,
                'user': user,
                'password': password,
                'db': db
            })

            self.databaseCursor = self.databaseConnection.cursor()
            self.databaseCursor.execute('SET NAMES utf8;')
            self.databaseCursor.execute('SET CHARACTER SET utf8;')
            self.databaseCursor.execute('SET character_set_connection=utf8;')
            self.databaseCursor.execute('SET GLOBAL connect_timeout=28800;')
            self.databaseCursor.execute('SET GLOBAL wait_timeout=28800;')
            self.databaseCursor.execute('SET GLOBAL interactive_timeout=28800;')

            return True

        except (MySQLdb.Error, MySQLdb.Error) as e:
            Logger.err("Database connection failed: " + str(e))
            return False

    def query(self, query, tryAgain = False):
        if len(query)< 100:
            Logger.dbg(u'SQL query: "{}"'.format(query))

        try:
            self.databaseCursor.execute(query)
            result = self.databaseCursor.fetchall()

            num = 0
            for row in result:
                num += 1
                if num > 5: break
                if len(str(row)) < 100:
                    Logger.dbg(u'Query ("{}") results:\nRow {}.: '.format(unicode(query), num) + str(row))
                else:
                    Logger.dbg(u'Query is too long')

            return result

        except (MySQLdb.Error, MySQLdb.Error) as e:
            if Database.checkIfReconnectionNeeded(e):
                if tryAgain == False:
                    Logger.err("Query ('{}') failed. Need to reconnect.".format(query))
                    self.reconnect()
                    return self.query(query, True)

            Logger.err("Query ('{}') failed: ".format(query) + str(e))
            return False

    @staticmethod
    def checkIfReconnectionNeeded(error):
        return ("MySQL server has gone away" in error[1])

    def reconnect(self):
        Logger.info("Trying to reconnect after failure...")
        if self.databaseConnection != None:
            try:
                self.databaseConnection.close()
            except:
                pass
            finally:
                self.databaseConnection = None

        self.connection(
            self.lastUsedCredentials['host'],
            self.lastUsedCredentials['user'],
            self.lastUsedCredentials['password'],
            self.lastUsedCredentials['db']
        )

    def insert(self, query, tryAgain = False):
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
        if len(query)< 100:
            Logger.dbg(u'SQL INSERT query: "{}"'.format(query))
        else:
            Logger.dbg(u'Query is too long')
        assert not query.lower().startswith('select '), "Method insert() must NOT be invoked with SELECT queries!"

        try:
            self.databaseCursor.execute(query)

            # Commit new records to the database
            result = self.databaseConnection.commit()
            return True, 1, None

        except (MySQLdb.Error, MySQLdb.Error) as e:
            try:
                # Rollback introduced changes
                self.databaseConnection.rollback()
            except: pass

            if Database.checkIfReconnectionNeeded(e):
                if tryAgain == False:
                    Logger.err("Insert query ('{}') failed. Need to reconnect.".format(query))
                    self.reconnect()
                    return self.insert(query, True)

            Logger.err("Insert Query ('{}') failed: ".format(query) + str(e))
            return False, e.args[0], e.args[1]

    def delete(self, query):
        assert query.lower().startswith('delete '), "Method delete() must be invoked only with DELETE queries!"
        return self.insert(query)