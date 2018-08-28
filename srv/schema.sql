-- Initialize the database.
-- Drop any existing data and create empty tables.

CREATE DATABASE IF NOT EXISTS przepisy;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS photos;
DROP TABLE IF EXISTS steps;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS users_recipes_stars;

CREATE TABLE sessions (
    session_id int(11) NOT NULL AUTO_INCREMENT,
    user_id int(11) NOT NULL,
    token varchar(80) DEFAULT NULL,
    created datetime NOT NULL,
    PRIMARY KEY (session_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE users (
    user_id int(11) NOT NULL AUTO_INCREMENT,
    user_login varchar(80) unique DEFAULT NULL,
    user_password varchar(80) DEFAULT NULL,
    first_name varchar(50) DEFAULT NULL,
    last_name varchar(50) DEFAULT NULL,
    email varchar(80) NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE recipes (
    recipe_id int(11) NOT NULL AUTO_INCREMENT,
    user_id int(11),
    recipe_name varchar(80) NOT NULL,
    recipe_description varchar(5000) NOT NULL,
    recipe_prepare_time time DEFAULT NULL,
    recipe_cook_time time DEFAULT NULL,
    recipe_bake_time time DEFAULT NULL,
    recipe_main_picture_id blob, DEFAULT NULL,
    recipe_category varchar(80) NOT NULL,
    recipe_created_date_time datetime,
    PRIMARY KEY (recipe_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recipe_main_picture_id) REFERENCES photos(photo_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE steps (
    step_id int(11) NOT NULL AUTO_INCREMENT,
    recipe_id int(11),
    photo_image blob,
    step_number int(11) NOT NULL,
    step_description varchar(5000),
    PRIMARY KEY (step_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    FOREIGN KEY (photo_id) REFERENCES photos(photo_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE ingredients (
    ingredient_id int(11) NOT NULL AUTO_INCREMENT,
    recipe_id int(11),
    ingredient_quantity int(11),
    ingredient_unit varchar(100),
    ingredient_name varchar(100),
    group_ingredient_name varchar(100),
    PRIMARY KEY (ingredient_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE comments (
    comment_id int(11) NOT NULL AUTO_INCREMENT,
    recipe_id int(11) NOT NULL,
    user_id int(11) NOT NULL,
    comment varchar(5000) NOT NULL,
    created_date datetime,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE users_recipes_stars (
    user_id int(11) NOT NULL,
    recipe_id int(11) NOT NULL,
    favorite boolean,
    stars int(1),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
