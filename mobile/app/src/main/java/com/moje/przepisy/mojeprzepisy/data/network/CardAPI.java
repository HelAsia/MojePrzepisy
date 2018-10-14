package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CardAPI {

  @GET("/cards/default")
  Call<List<OneRecipeCard>> getCards();

  @GET("/cards/alphabetically")
  Call<List<OneRecipeCard>> getCardsSortedAlphabetically();

  @GET("/cards/lastAdded")
  Call<List<OneRecipeCard>> getCardsSortedByLastAdded();

  @GET("/cards/highestRated")
  Call<List<OneRecipeCard>> getCardsSortedByHighestRated();

  @GET("/cards/favorite")
  Call<List<OneRecipeCard>> getCardsSortedByFavorite();

  @POST("cards/searchedCards")
  Call<List<OneRecipeCard>> getCardsSortedBySearchedQuery(@Body OneRecipeCard oneRecipeCard);

  @GET("/cards/userCards")
  Call<List<OneRecipeCard>> getUserCards();

  @GET("/cards/{recipeId}")
  Call<List<OneRecipeCard>> getUpdatedCard(@Path("recipeId") int recipeId);
}
