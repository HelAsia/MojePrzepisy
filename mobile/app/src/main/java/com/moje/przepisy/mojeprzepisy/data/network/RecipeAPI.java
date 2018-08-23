package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.PhotoRecipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeAPI {

  @GET("recipe/{recipeId}")
  Call<List<Recipe>> getRecipe(@Path("recipeId") int recipeId);

  @POST("recipe/{recipeId}/{columnName}/{columnValue}")
  Call<List<Recipe>> editRecipe(@Path("recipeId") int recipeId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe")
  Call<List<Recipe>> addRecipe(@Body Recipe recipe);

  @DELETE("recipe/{recipeId}")
  Call<List<Recipe>> deleteRecipe(@Path("recipeId") int recipeId, @Body Recipe recipe);


  @GET("recipe/step/{recipeId}")
  Call<List<Step>> getStep(@Path("recipeId") int recipeId);

  @POST("recipe/step/{stepId}/{columnName}/{columnValue}")
  Call<List<Step>> editStep(@Path("stepId") int stepId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe/step")
  Call<List<Step>> addStep(@Body Step step);

  @DELETE("recipe/step/{stepId}")
  Call<List<Step>> deleteStep(@Path("stepId") int stepId, @Body Step step);


  @GET("recipe/ingredient/{recipeId}")
  Call<List<Ingredient>> getIngredient(@Path("recipeId") int recipeId);

  @POST("recipe/ingredient/{ingredientId}/{columnName}/{columnValue}")
  Call<List<Ingredient>> editIngredient(@Path("ingredientId") int ingredientId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe/ingredient")
  Call<List<Ingredient>> addIngredient(@Body Ingredient ingredient);

  @DELETE("recipe/ingredient/{ingredientId}")
  Call<List<Ingredient>> deleteIngredient(@Path("ingredientId") int ingredientId, @Body Ingredient ingredient);


  @GET("recipe/comment/{recipeId}")
  Call<List<Comment>> getComment(@Path("recipeId") int recipeId);

  @POST("recipe/comment/{commentId}/{columnName}/{columnValue}")
  Call<List<Comment>> editComment(@Path("commentId") int commentId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe/comment")
  Call<List<Comment>> addComment(@Body Comment comment);

  @DELETE("recipe/comment/{commentId}")
  Call<List<Comment>> deleteComment(@Path("commentId") int commentId, @Body Comment comment);


  @GET("recipe/photo/{photoId}")
  Call<List<PhotoRecipe>> getPhoto(@Path("photoId") int photoId);

  @POST("recipe/photo/{photoId}/{columnName}/{columnValue}")
  Call<List<PhotoRecipe>> editPhoto(@Path("photoId") int photoId, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe/photo")
  Call<List<PhotoRecipe>> addPhoto(@Body PhotoRecipe photoRecipe);

  @DELETE("recipe/photo/{commentId}")
  Call<List<PhotoRecipe>> deletePhoto(@Path("photoId") int photoId, @Body PhotoRecipe photoRecipe);


}
