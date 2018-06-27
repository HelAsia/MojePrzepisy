#!/usr/bin/python
#
# 	This class is an implementation of simple Session management mechanism,
# that is based on MySQL table holding:
#		- session_id
#		- user_id
#		- token
#		- created
# 
# The mechanism is capable of creating and destroying session tokens,
# validating them and also removing expired ones.
#
# Mariusz B. / mgeeky, '18
#

from Logger import *
import random
import string
from datetime import datetime

class Session:

	# Specifies how long should get token's value.
	SESSION_TOKEN_LENGTH = 32

	# Specifies name for the token's parameter to store in session cookie.
	SESSION_TOKEN_NAME = 'token'

	# Specifies maximum number of minutes the session should stay active.
	# After that amount of time, the session token is considered expired.
	SESSION_EXPIRE_AFTER_MINUTES = 30 * 24 * 60

	# Flask session global object.
	session = None

	# Database connection handle.
	database = None

	token_value = ''


	def __init__(self, session, database):
		self.session = session
		self.database = database

		if Session.SESSION_TOKEN_NAME in session:
			self.token_value = Session.sanitize_token(
				session[Session.SESSION_TOKEN_NAME]
			)

		self.destroy_expired_sessions()


	@staticmethod
	def sanitize_token(token):
		'''
			Sanitizes input token's value for later usage in SQL queries, to
		avoid SQL Injection attempts when the attacker would manipulate
		session cookie's inner JSON's value.

		params:
			token 	Token value to sanitize.

		returns:
			Token's value with every non-letter and non-digit character cut out.
		'''

		new_token = ''
		for c in token:
			if c in string.ascii_letters + string.digits:
				new_token += c

		return new_token

	@staticmethod
	def _generate_token_value():
		'''
			Generates random string with value specified in class' Constant.
		'''

		chars = string.ascii_letters + string.digits
		return ''.join(random.choice(chars) for _ in range(Session.SESSION_TOKEN_LENGTH))


	@staticmethod
	def _get_datetime():
		now = datetime.now()
		return now.strftime('%Y-%m-%d %H:%M:%S')


	def create_session(self, user_id):
		'''
			Creates new session's token by generating a new unique value for it,
		then clearing and setting Flask's session object and ultimately creating
		a record in a database.

		params:
			user_id 	ID of user to whom link that access token.

		returns:
			True when succeeded, False otherwise.
		'''

		user_id = abs(int(user_id))

		# Generate new, unique session token's value.
		self.token_value = Session._generate_token_value()
		created = Session._get_datetime()

		Logger.dbg('({}) Creating new session token for user {}: "{}"'.format(
			created, user_id, self.token_value
		))

		# Clear any previous Flask's session contents.
		self.session.clear()
		self.session[Session.SESSION_TOKEN_NAME] = self.token_value

		query = u'INSERT INTO sessions (session_id, user_id, token, created) ' \
				u"VALUES('{}', '{}', '{}', '{}')".format(
					0, user_id, self.token_value, created
				)

		return self.database.insert(query) == 1


	def destroy_session(self):
		'''
			Destroys valid session by removing it from a database and clearing
		Flask's session object.
		'''

		self.session.clear()

		if not self.token_value:
			return

		query = u"DELETE FROM sessions WHERE token='{}'".format(
			self.token_value
		)

		Logger.dbg('Invalidating session with token: "{}"'.format(
			self.token_value
		))

		self.database.delete(query)


	def destroy_all_sessions(self, user_id):
		'''
			Removes every session token linked with supplied user_id.

		params:
			user_id		ID of the user whose tokens to delete.
		'''
		user_id = abs(int(user_id))
		if not user_id or user_id < 0:
			return

		query = u"DELETE FROM sessions WHERE user_id='{}'".format(
			user_id
		)

		Logger.dbg('Invalidating all session of user: "{}"'.format(
			user_id
		))

		self.session.clear()
		self.database.delete(query)


	def destroy_expired_sessions(self):
		'''
			Collects all created session tokens and verifies every each of them
		whether it has expired or not. Expired tokens are then removed.
		'''

		res = self.database.query("SELECT * FROM sessions")
		if res:
			removed = 0
			for row in res:
				created = row['created'].strftime('%Y-%m-%d %H:%M:%S')
				if Session._check_if_expired(created):
					removed += 1
					self.database.delete(
						u"DELETE FROM sessions WHERE token='{}'".format(
							row['token']
					))

			if removed > 0:
				Logger.info("Removed {} expired session tokens.".format(removed))


	@staticmethod
	def _check_if_expired(created_timestamp):
		'''
			Computes difference between current time and time given and 
		returns True if that difference in minutes is below threshold.

		params:
			created_timestamp	Timestamp to compare with current one.

		return:
			Returns True if that difference in minutes is below threshold.
		'''
		created = datetime.strptime(created_timestamp, '%Y-%m-%d %H:%M:%S')
		now = datetime.now()

		delta = now - created
		minutes = divmod(delta.days * 86400 + delta.seconds, 60)

		return minutes[0] > Session.SESSION_EXPIRE_AFTER_MINUTES


	def validate_session(self):
		'''
			Checks whether the session is still active (and thus can be cosidered
		valid). It firstly lookups for records in sessions table and if finds
		any, checks whether such session has not expired yet. If it does,
		then destroys that session.

		return:
			Returns True if session is still valid, False otherwise.
		'''

		if not self.token_value:
			return False

		query = u"SELECT * FROM sessions WHERE token='{}'".format(
			self.token_value
		)

		res = self.database.query(query)
		if res:
			# It seems there has been a session token created with that value.
			res = res[0]
			created = res['created'].strftime('%Y-%m-%d %H:%M:%S')
			
			Logger.dbg('There is a session for user: {}, created at: {}'.format(
				res['user_id'], created
			))

			if Session._check_if_expired(created):
				Logger.fail('The session has expired.: token = "{}"'.format(
					self.token_value
				))

				self.destroy_session()
				return False

			return True	
		return False


	def get_user_id(self):
		'''
			Returns currently authenticated user ID based on supplied token's
		value. If there is no session token, or in case of other failure - 
		returns -1.
		'''
		if not self.token_value:
			Logger.err("Could not get user's ID since there is no token!")
			return -1

		query = u"SELECT * FROM sessions WHERE token='{}'".format(
			self.token_value
		)

		res = self.database.query(query)
		if res:
			return res[0]['user_id']

		return -1