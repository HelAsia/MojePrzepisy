#!flask/bin/python
from flask import Flask, jsonify, request, session
import os
import sys

# My libraries
from Constans import *
from Database import *
from Users import *

# Global definitions
app = Flask(__name__)
database = None


# Code

# Login session
@app.route('/user/login', methods=['POST'])
def login_method():
    user = Users(database)
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    status, message, userID = user.loginUser(login, password)

    if status == 200:
        session['logged_in'] = True
        session['user_id'] = userID

        Logger.dbg('login(): userID = {}'.format(str(userID)))
        Logger.ok('User "{}" has been logged.'.format(login))
    else:
        Logger.fail('Could not authenticate user: "{}"'.format(login))

    return jsonify({
        'status': status,
        'message': message
    })


# Logout session
@app.route('/user/logout', methods=['GET'])
def logout_method():
    session.pop('user_id', None)
    session.pop('logged_in', None)
    session.clear()

    return jsonify({
        'status': 200,
        'message': 'You have been logged out.'
    })


# Operations performed on the profile
@app.route('/user/profile', methods=['PUT', 'GET', 'DELETE', 'POST'])
def profile_method():
    user = Users(database)

    # Register
    if request.method == 'PUT':
        params = request.get_json()

        login = params.get('login')
        password = params.get('password')
        firstName = params.get('firstName')
        lastName = params.get('lastName')
        email = params.get('email')

        status, message = user.registerUser(login, password, firstName, lastName, email)

        return jsonify({
            'status': status,
            'message': message
        })
    # Show user data
    elif request.method == 'GET':
        #return jsonify(user.getUser(session['user_id']))
        return jsonify({})
    # Delete user
    elif request.method == 'DELETE':
        status, message = user.deleteUser(login, password)

        return jsonify({
            'status': status,
            'message': message
        })

    # Edit user
    elif request.method == 'POST':
        return jsonify({
            'status': status,
            'message': message
        })


def main():
    global database
    database = Database()
    if not database.connection(host=HOST, user=USER, password=PASSWORD, db=DATABASE):
        Logger.err("Database connection failed")
        return

    # This launches server
    app.secret_key = os.urandom(32)
    app.run(debug=True)

if __name__ == '__main__':
    main()
