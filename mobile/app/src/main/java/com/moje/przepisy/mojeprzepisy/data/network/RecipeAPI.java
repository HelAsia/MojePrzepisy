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

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
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
  Single<Recipe> getRecipe(@Path("id") int id);

  @POST("recipe/{id}/{columnName}/{columnValue}")
  Completable editRecipe(@Path("id") int id, @Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @PUT("recipe")
  Completable addWholeRecipeElements(@Body RecipeAllElements recipeAllElements);

  @DELETE("recipe/{id}")
  Completable deleteRecipe(@Path("id") int id);


  @FormUrlEncoded
  @PUT("recipe/photo")
  Single<Message> addPhoto(@Field("photo") String photoString);

  @POST("recipe/photo/{id}")
  Completable editPhoto(@Path("id") int id, @Body Photo photo);

  @DELETE("recipe/photo/{id}")
  Completable deletePhoto(@Path("id") int id, @Body Photo photo);


  @GET("recipe/{id}/step")
  Single<List<Step>> getStep(@Path("id") int recipeId);

  @POST("recipe/step/{id}/{columnName}/{columnValue}")
  Completable editStep(@Path("id") int id, @Path("columnName") String columnName,
                            @Path("columnValue") String columnValue);

  @DELETE("recipe/step/{id}")
  Completable deleteStep(@Path("id") int id, @Body Step step);


  @GET("recipe/{id}/ingredient")
  Single<List<Ingredient>> getIngredient(@Path("id") int id);

  @POST("recipe/ingredient/{id}/{columnName}/{columnValue}")
  Completable editIngredient(@Path("id") int id, @Path("columnName") String columnName,
                                        @Path("columnValue") String columnValue);

  @DELETE("recipe/ingredient/{id}")
  Completable deleteIngredient(@Path("id") int id, @Body Ingredient ingredient);


  @GET("recipe/{id}/comment")
  Single<List<Comment>> getComment(@Path("id") int id);

  @POST("recipe/comment/{id}/{columnName}/{columnValue}")
  Completable editComment(@Path("id") int id, @Path("columnName") String columnName,
                                  @Path("columnValue") String columnValue);

  @PUT("recipe/comment")
  Completable addComment(@Body Comment comment);

  @DELETE("recipe/comment/{id}")
  Completable deleteComment(@Path("id") int id);

  @GET("recipe/{id}/stars/detail")
  Single<List<Stars>> getRecipeDetailsStars(@Path("id") int id);

  @POST("recipe/{id}/stars/{columnName}/{columnValue}")
  Completable editStarsAndHeart(@Path("id") int recipeId, @Path("columnName") String columnName,
                                @Path("columnValue") int columnValue);
}
