-- Initialize the database.
-- Drop any existing data and create empty tables.

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS sessions;

CREATE TABLE users (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  user_login varchar(80) unique DEFAULT NULL,
  user_password varchar(80) DEFAULT NULL,
  first_name varchar(50) DEFAULT NULL,
  last_name varchar(50) DEFAULT NULL,
  email varchar(80) NOT NULL,
  PRIMARY KEY (user_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE sessions (
  session_id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  token varchar(80) DEFAULT NULL,
  created datetime NOT NULL,
  PRIMARY KEY (session_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
