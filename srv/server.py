#!flask/bin/python
from flask import Flask, jsonify, request
import sys

# My libraries
from Constans import *
from Database import *
from Users import *


# Global definitions
app = Flask(__name__)
database = None


# Code
@app.route('/')
def index():
    return 'This is the home page.'


@app.route('/user/login', methods=['POST',])
def login_method():
    user = Users(database)
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    status, message = user.loginUser(login,password)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/user/profile', methods=['PUT', 'GET', 'DELETE', 'POST'])
def profile_method():
    user = Users(database)
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    if request.method == 'PUT':
        firstName = params.get('firstName')
        lastName = params.get('lastName')
        email = params.get('email')

        status, message = user.registerUser(login, password, firstName, lastName, email)

        return jsonify({
            'status': status,
            'message': message
        })
    elif request.method == 'GET':
        status, message = user.getUser(login, password)
        return jsonify({
            'status': status,
            'message': message
        })
    elif request.method == 'DELETE':
        status, message = user.deleteUser(login, password)
        return jsonify({
            'status': status,
            'message': message
        })
    elif request.method == 'POST':
        return jsonify({
            'status': status,
            'message': message
        })

    
def main():
    global database
    database = Database()
    if not database.connection(host=HOST, user=USER, db=DATABASE):
        Logger.err("Database connection failed")
        return

    # This launches server
    app.run(debug=True)


if __name__ == '__main__':
    main()