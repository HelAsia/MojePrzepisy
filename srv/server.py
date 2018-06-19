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


@app.route('/user/login', methods=['POST'])
def login_method():
    user = Users()
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    status, message = user.login(login,password)

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
    app.run(debug=True)

if __name__ == '__main__':
    main()