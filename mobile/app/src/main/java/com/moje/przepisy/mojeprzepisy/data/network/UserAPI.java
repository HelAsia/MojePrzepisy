package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

  @POST("/login")
  Call<Message> login(@Body User user);

  @GET("/logout")
  Call<Message> logout();


  @PUT("/user")
  Call<Message> register(@Body User user);

  @GET("/user")
  Call<Message> getUser();

  @POST("/user/{columnName}/{columnValue}")
  Call<Message> editUser(@Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @DELETE("/user")
  Call<Message> deleteUser();


  @GET("/cards/default")
  Call<List<OneRecipeCard>> getCards();

  @GET("/cards/alphabetically")
  Call<List<OneRecipeCard>> getCardsSortedAlphabetically();

  @GET("/cards/lastAdded")
  Call<List<OneRecipeCard>> getCardsSortedByLastAdded();

  @GET("/cards/highestRated")
  Call<List<OneRecipeCard>> getCardsSortedByHighestRated();
  
  @POST("cards/searchedCards")
  Call<List<OneRecipeCard>> getCardsSortedBySearchedQuery(@Body OneRecipeCard oneRecipeCard);


  @GET("/cards/userCards")
  Call<List<OneRecipeCard>> getUserCards();

  @POST("/cards/userCards")
  Call<Message> changeUserCards(@Body OneRecipeCard oneRecipeCard);

  @DELETE("/cards/userCards")
  Call<Message> deleteUserCards(@Body OneRecipeCard oneRecipeCard);


  @GET("recipe/{recipeId}")
  Call<List<Recipe>> getRecipe(@Path("recipeId") int recipeId);

  @POST("recipe/{recipeId}/{columnName}/{columnValue}")
  Call<List<Recipe>> editRecipe(@Path("recipeId") int recipeId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe")
  Call<List<Recipe>> addRecipe(@Body Recipe recipe);

  @DELETE("recipe/{recipeId}")
  Call<List<Recipe>> deleteRecipe(@Path("recipeId") int recipeId, @Body Recipe recipe);

}
