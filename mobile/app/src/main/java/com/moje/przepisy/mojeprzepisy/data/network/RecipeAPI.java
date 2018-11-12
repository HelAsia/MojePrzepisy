package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.Photo;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.RecipeAllElements;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeAPI {
  @GET("recipe/{recipeId}")
  Call<Recipe> getRecipe(@Path("recipeId") int recipeId);

  @POST("recipe/{recipeId}/{columnName}/{columnValue}")
  Call<List<Recipe>> editRecipe(@Path("recipeId") int recipeId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe")
  Call<Message> addWholeRecipeElements(@Body RecipeAllElements recipeAllElements);

  @DELETE("recipe/{recipeId}")
  Call<Message> deleteRecipe(@Path("recipeId") int recipeId);


  @FormUrlEncoded
  @PUT("recipe/photo")
  Call<Message> addPhoto(@Field("photo") String photoString);

  @POST("recipe/photo/{photoId}")
  Call<List<Photo>> editPhoto(@Path("photoId") int photoId, @Body Photo photo);

  @DELETE("recipe/photo/{photoId}")
  Call<List<Photo>> deletePhoto(@Path("photoId") int photoId, @Body Photo photo);


  @GET("recipe/step/{recipeId}")
  Call<List<Step>> getStep(@Path("recipeId") int recipeId);

  @POST("recipe/step/{stepId}/{columnName}/{columnValue}")
  Call<List<Step>> editStep(@Path("stepId") int stepId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @DELETE("recipe/step/{stepId}")
  Call<Message> deleteStep(@Path("stepId") int stepId, @Body Step step);


  @GET("recipe/ingredient/{recipeId}")
  Call<List<Ingredient>> getIngredient(@Path("recipeId") int recipeId);

  @POST("recipe/ingredient/{ingredientId}/{columnName}/{columnValue}")
  Call<List<Ingredient>> editIngredient(@Path("ingredientId") int ingredientId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @DELETE("recipe/ingredient/{ingredientId}")
  Call<Message> deleteIngredient(@Path("ingredientId") int ingredientId, @Body Ingredient ingredient);


  @GET("recipe/comment/{recipeId}")
  Call<List<Comment>> getComment(@Path("recipeId") int recipeId);

  @POST("recipe/comment/{commentId}/{columnName}/{columnValue}")
  Call<List<Comment>> editComment(@Path("commentId") int commentId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe/comment")
  Call<Message> addComment(@Body Comment comment);

  @DELETE("recipe/comment/{commentId}")
  Call<Message> deleteComment(@Path("commentId") int commentId);

  @GET("recipe/stars/detail/{recipeId}")
  Call<List<Stars>> getRecipeDetailsStars(@Path("recipeId") int recipeId);

  @POST("recipe/stars/{recipeId}/{columnName}/{columnValue}")
  Call<Message> editStarsAndHeart(@Path("recipeId") int recipeId, @Path("columnName") String columnName, @Path("columnValue") int columnValue);

  @PUT("recipe/stars")
  Call<Message> addStars(@Body Stars stars);

  @DELETE("recipe/stars/{recipeId}")
  Call<Message> deleteStars(@Path("recipeId") int recipeId);

  @GET("recipe/favorite/{recipeId}")
  Call<Stars> getFavorite(@Path("recipeId") int recipeId);

}
