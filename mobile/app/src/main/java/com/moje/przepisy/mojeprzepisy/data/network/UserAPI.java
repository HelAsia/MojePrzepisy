package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserAPI {

  @POST("/user/login")
  Call<Message> login(@Body User user);

  @GET("/user/logout")
  Call<Message> logout(@Body User user);

  @GET("/user/profile")
  Call<Message> getUser();

  @PUT("/user/profile")
  Call<Message> register(@Body User user);

  @GET("/cards/allCards")
  Call<List<OneRecipeCard>> getCards();

  @GET("/cards/allCardsSortedAlphabetically")
  Call<List<OneRecipeCard>> getCardsSortedAlphabetically();

  @GET("/cards/allCardsSortedByLastAdded")
  Call<List<OneRecipeCard>> getCardsSortedByLastAdded();

  @GET("/cards/allCardsSortedByHighestRated")
  Call<List<OneRecipeCard>> getCardsSortedByHighestRated();

  @GET("/cards/userCards")
  Call<List<OneRecipeCard>> getUserCards();

  @POST("/cards/userCards")
  Call<Message> changeUserCards(@Body OneRecipeCard oneRecipeCard);

  @DELETE("/cards/userCards")
  Call<Message> deleteUserCards(@Body OneRecipeCard oneRecipeCard);



}
