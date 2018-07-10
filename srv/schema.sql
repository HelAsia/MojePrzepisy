-- Initialize the database.
-- Drop any existing data and create empty tables.

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS users_recipes;

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

CREATE TABLE recipes (
    recipe_id int(11) NOT NULL AUTO_INCREMENT,
    recipe_name varchar(80) NOT NULL,
    recipe_description varchar(5000) NOT NULL,
    recipe_prepare_time time DEFAULT NULL,
    recipe_cook_time time DEFAULT NULL,
    recipe_bake_time time DEFAULT NULL,
    recipe_link varchar(800) DEFAULT NULL,
    recipe_category varchar(80) NOT NULL,
    user_id int(11),
    PRIMARY KEY (recipe_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE users_recipes_stars (
	user_id int(11) NOT NULL,
    recipe_id int(11) NOT NULL,
    favorite boolean,
    stars int(1),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
)ENGINE=MyISAM AUTO DEFAULT CHARSET=utf8;


SELECT R.recipe_name AS Recipe, U.user_login AS User, count(URS.favorite) AS Favorite, ROUND(avg(URS.stars),0) AS Stars
    FROM recipes AS R
    INNER JOIN users AS U
    ON R.user_id = U.user_id
    INNER JOIN users_recipes_stars AS URS
    ON R.recipe_id = URS.recipe_id
    GROUP BY R.recipe_id;