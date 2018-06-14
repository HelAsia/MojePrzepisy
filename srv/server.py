#!flask/bin/python
from flask import Flask, jsonify, request

app = Flask(__name__)

tasks = [
    {
        'id': 1,
        'title': u'Buy groceries',
        'description': u'Milk, Cheese, Pizza, Fruit, Tylenol',
        'done': False
    },
    {
        'id': 2,
        'title': u'Learn Python',
        'description': u'Need to find a good Python tutorial on the web',
        'done': False
    }
]

@app.route('/')
def index():
    return 'This is the home page.'

@app.route('/something')
def something():
    return 'You showed something'

@app.route('/profile/<username>')
def profile(username):
    return 'Hey there %s' % username

@app.route('/post/<int:post_id>')
def show_post(post_id):
    return 'Post ID is %s' %post_id


@app.route('/todo/api/v1.0/tasks', methods=['GET'])
def get_tasks():
    return jsonify({'tasks' : tasks})

@app.route('/login', methods = ['POST'])
def login():
    username = request.args.get('login', '')
    password = request.args.get('password', '')
    return jsonify({
        'status': 200,
        'message': 'Przekazano login={}, haslo={}'.format(username, password)
    })

if __name__ == '__main__':
    app.run(debug=True)