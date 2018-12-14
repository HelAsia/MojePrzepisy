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
  @GET("recipe/{id}")
  Call<Recipe> getRecipe(@Path("id") int id);

  @POST("recipe/{id}/{columnName}/{columnValue}")
  Call<List<Recipe>> editRecipe(@Path("id") int id, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe")
  Call<Message> addWholeRecipeElements(@Body RecipeAllElements recipeAllElements);

  @DELETE("recipe/{id}")
  Call<Message> deleteRecipe(@Path("id") int id);


  @FormUrlEncoded
  @PUT("recipe/photo")
  Call<Message> addPhoto(@Field("photo") String photoString);

  @POST("recipe/photo/{id}")
  Call<List<Photo>> editPhoto(@Path("id") int id, @Body Photo photo);

  @DELETE("recipe/photo/{id}")
  Call<List<Photo>> deletePhoto(@Path("id") int id, @Body Photo photo);


  @GET("recipe/{id}/step")
  Call<List<Step>> getStep(@Path("id") int recipeId);

  @POST("recipe/step/{id}/{columnName}/{columnValue}")
  Call<List<Step>> editStep(@Path("id") int id, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @DELETE("recipe/step/{id}")
  Call<Message> deleteStep(@Path("id") int id, @Body Step step);


  @GET("recipe/{id}/ingredient")
  Call<List<Ingredient>> getIngredient(@Path("id") int id);

  @POST("recipe/ingredient/{id}/{columnName}/{columnValue}")
  Call<List<Ingredient>> editIngredient(@Path("id") int id, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @DELETE("recipe/ingredient/{id}")
  Call<Message> deleteIngredient(@Path("id") int id, @Body Ingredient ingredient);


  @GET("recipe/{id}/comment")
  Call<List<Comment>> getComment(@Path("id") int id);

  @POST("recipe/comment/{id}/{columnName}/{columnValue}")
  Call<List<Comment>> editComment(@Path("id") int id, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe/comment")
  Call<Message> addComment(@Body Comment comment);

  @DELETE("recipe/comment/{id}")
  Call<Message> deleteComment(@Path("id") int id);

  @GET("recipe/{id}/stars/detail")
  Call<List<Stars>> getRecipeDetailsStars(@Path("id") int id);

  @POST("recipe/{id}/stars/{columnName}/{columnValue}")
  Call<Message> editStarsAndHeart(@Path("id") int recipeId, @Path("id") String columnName, @Path("columnValue") int columnValue);

  @PUT("recipe/stars")
  Call<Message> addStars(@Body Stars stars);

  @DELETE("recipe/{id}/stars")
  Call<Message> deleteStars(@Path("id") int id);

  @GET("recipe/{id}/favorite")
  Call<Stars> getFavorite(@Path("id") int id);

}
