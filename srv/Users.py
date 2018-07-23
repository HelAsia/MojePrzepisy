from Logger import *
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
        query = u"SELECT user_id " \
                u"FROM users " \
                u"WHERE user_login = '{}' AND user_password = '{}'".format(login, password)

        queryResult = self.database.query(query)

        if queryResult:
            return 200, u'You are logged in', queryResult[0]['user_id']
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
                    return 200, u'Registered login={}, first name={}, last name={}, email={}'.format(
                        login, firstName, lastName, email
                    )
                elif not status:
                    if queryResult == 1062:
                        return 404, u'This login exists in Database'
                    else:
                        return 500, errorMessage
            else:
                return 500, u'Wrong type of email'
        else:
            return 500, u'Wrong type of password'



    def getUser(self,userID):
        query = u"SELECT user_login, first_name, last_name, email " \
                u"FROM users " \
                u"WHERE user_id = '{}'".format(userID)
        queryResult = self.database.query(query)

        if queryResult:
            return queryResult[0]
        else:
            return {}


    def deleteUser(self,login, password):
        query = u"DELETE FROM users " \
                u"WHERE user_login = '{}' AND user_password = '{}'".format(login, password)
        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted login={},  password={}, first name={}, last name={}, email='.format(login, password, firstName, lastName, email)
        else:
            return 404, u'Forwarded data to check are not correct'


    def getAllCards(self):
        query = u"SELECT R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}


    def getAllCardsSortedAlphabetically(self):
        query = u"SELECT R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id " \
                u"ORDER BY R.recipe_name; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}


    def getAllCardsSortedByLastAdded(self):
        query = u"SELECT R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id " \
                u"ORDER BY R.date_time; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}


    def getAllCardsSortedByHighestRated(self):
        query = u"SELECT R.recipe_name AS recipeName, U.user_login AS authorName, " \
                u"count(URS.favorite) AS favoritesCount, ROUND(avg(URS.stars),0) AS starsCount, " \
                u"R.recipe_main_picture as photoRecipe, R.date_time as Date "\
                u"FROM recipes AS R "\
                u"INNER JOIN users AS U "\
                u"ON R.user_id = U.user_id "\
                u"INNER JOIN users_recipes_stars AS URS "\
                u"ON R.recipe_id = URS.recipe_id "\
                u"GROUP BY R.recipe_id " \
                u"ORDER BY ROUND(avg(URS.stars),0) DESC; "

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}
