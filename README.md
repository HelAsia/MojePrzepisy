![logo](Photo/logo.png)

# *Przepisy domowe Helasia*

*Przepisy domowe Helasia* is my first complex Android application where I learned many programming concepts and tried to make useful tool in everyday life.

The application is not finished yet. It is still being developed.

---


## Working Features
1. Users registration
2. Log in user (remember logged user)
3. Log out user
4. Ability to use application as a guest
5. Add recipe
6. Comments on recipes
7. Like recipes of other users
8. Rate recipes of other users
9. Create favourites list
10. Search recipes sorted by:
 - recipe name
 - last added
 - highest rated
 - category
 - favourites count
11. Show recipe:
 - main infos *(photo, category, prepare time, bake time, cook time)*
 - recipe's ingredients
 - steps
 - comments
12. Edit user profile:
 - photo
 - first name
 - last name
 - email
 - password
13. Kitchen Timers
14. Delete recipe

## To do:
 1. Edit recipe
 2. Delete user's account
 3. Edit comment
 4. Delete comment
 5. Update timer *(alarm after finish countdown)*
 6. Application's description
 7. Used third-party code and it's licences
 8. Different layout for horizontal screens
 9. Tests (unit and integration)

## Known bugs:
 1. Optimize loading photos and data receival from server
 2. Wrong number of just added step

## Credits:
#### 1. Libraries
[Support Library Packages](https://developer.android.com/topic/libraries/support-library/packages "Support Library Packages") - used to adjust and for better handling of list and grid views.

[Butter Knife](http://jakewharton.github.io/butterknife) - used to find and automatically cast the corresponding view in layout.

[Picasso](http://square.github.io/picasso) - used to speed up loading of images.

[Retrofit](https://square.github.io/retrofit) - used to manage connections with server and handle JSON to POJO deserialization with ease. Also retrofit handles Gzip decompression in the fly.

[Okhttp](http://square.github.io/okhttp) - used to support all requests and responses.

[Gson](https://github.com/google/gson) - used to convert Java Objects into their JSON representation.

[Flask](http://flask.pocoo.org) - used in Python for RESTful request dispatching and support for secure cookies (client side sessions).

#### 2. Other resources
[Food photos](https://pixabay.com/pl/photos/?cat=food) - used to download free of cost food images (e. g. category).

[Material design icons](https://github.com/google/material-design-icons) - used to download and set icons (e. g. in drawer).

[Free vector icons](https://www.flaticon.com/) - used to download and set icons (e. g. in drawer).

[Bitmap - String converter](http://vinayandroid.blogspot.com/2015/02/convert-image-bitmap-to-string.html) - used to convert image Bitmap to String and String to image Bitmap.


## Screens:
![first_screen](Photo/rev2/first_screen_rev2.PNG)
![register](Photo/rev2/register_rev2.PNG)
![login](Photo/rev2/login_rev2.PNG)
![registered_drawer_1](Photo/rev2/registered_drawer_1_rev2.PNG)
![registered_drawer_2](Photo/rev2/registered_drawer_2_rev2.PNG)
![sorting_method](Photo/rev2/sorting_method_rev2.PNG)
![category_option](Photo/rev2/category_option_rev2.PNG)
![user_profile_1](Photo/rev2/user_profile_1_rev2.PNG)
![recipe_details_1](Photo/rev2/recipe_details_1_rev2.PNG)
![recipe_details_2](Photo/rev2/recipe_details_2_rev2.PNG)
![recipe_details_3](Photo/rev2/recipe_details_3_rev2.PNG)
![recipe_details_4](Photo/rev2/recipe_details_4_rev2.PNG)
![add_recipe](Photo/rev2/add_recipe_1_rev2.PNG)
![add_ingredients](Photo/rev2/add_ingredients_rev2.PNG)
![add_step](Photo/rev2/add_step_rev2.PNG)
![display_recipe_1](Photo/rev2/display_recipe_1_rev2.PNG)
![display_recipe_2](Photo/rev2/display_recipe_2_rev2.PNG)
![display_recipe_3](Photo/rev2/display_recipe_3_rev2.PNG)
![timer](Photo/rev2/timer_rev2.PNG)
