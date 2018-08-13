package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CardAPI {

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

}
