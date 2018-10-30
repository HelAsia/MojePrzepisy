package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.cards;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.network.CardAPI;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OperationsOnCardRepository implements OperationsOnCardRepositoryInterface {

  private Retrofit retrofit;
  private CardAPI cardAPI;

  public OperationsOnCardRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.cardAPI = retrofit.create(CardAPI.class);
  }

  @Override
  public void getCardsSortedByChoseMethod(final OnCardsListener cardsListener, String method) {
    Call<List<OneRecipeCard>> resp = cardAPI.getCards();
    if (method.equals("default")){
      resp = cardAPI.getCards();
    }else if (method.equals("alphabetically")){
      resp = cardAPI.getCardsSortedAlphabetically();
    }else if (method.equals("lastAdded")){
      resp = cardAPI.getCardsSortedByLastAdded();
    }else if (method.equals("highestRated")){
      resp = cardAPI.getCardsSortedByHighestRated();
    }else if (method.equals("favorite")){
      resp = cardAPI.getCardsSortedByFavorite();
    }else if (method.equals("myRecipe")){
      resp = cardAPI.getUserCards();
    }
    resp.enqueue(new Callback<List<OneRecipeCard>>() {
      @Override
      public void onResponse(Call<List<OneRecipeCard>> call, Response<List<OneRecipeCard>> response) {
        List<OneRecipeCard> recipes = response.body();
        cardsListener.setRecipesList(recipes);

       for (OneRecipeCard recipe : recipes) {
          Log.i("getCardsSortedByChoseMethod.onResponse(): SERWER", "Id: " + recipe.recipeId);
          Log.i("getCardsSortedByChoseMethod.onResponse(): SERWER", "Author: " + recipe.authorName);
          Log.i("getCardsSortedByChoseMethod.onResponse(): SERWER", "Favorite: " + recipe.favoritesCount);
          Log.i("getCardsSortedByChoseMethod.onResponse(): SERWER", "PhotoRecipe: " + recipe.recipeMainPictureNumber);
          Log.i("getCardsSortedByChoseMethod.onResponse(): SERWER", "Recipe: " + recipe.recipeName);
          Log.i("getCardsSortedByChoseMethod.onResponse(): SERWER", "Stars: " + recipe.starsCount);
        }
      }
      @Override
      public void onFailure(Call<List<OneRecipeCard>> call, Throwable t) {
        Log.i("getCardsSortedByChoseMethod.onFailure(): SERWER", t.getMessage());
      }
    });
  }

  @Override
  public void getCardsSortedBySearchedQuery(final OnCardsListener cardsListener, String recipeName) {
    OneRecipeCard oneRecipeCard = new OneRecipeCard(recipeName, 1);
    Call<List<OneRecipeCard>> resp = cardAPI.getCardsSortedBySearchedQuery(oneRecipeCard);

    resp.enqueue(new Callback<List<OneRecipeCard>>() {
      @Override
      public void onResponse(Call<List<OneRecipeCard>> call, Response<List<OneRecipeCard>> response) {
        List<OneRecipeCard> recipes = response.body();
        cardsListener.setRecipesList(recipes);
      }
      @Override
      public void onFailure(Call<List<OneRecipeCard>> call, Throwable t) {
        Log.i("getCardsSortedByChoseMethod.onFailure(): SERWER", t.getMessage());
      }
    });
  }

  @Override
  public void getCardsSortedByCategoryQuery(final OnCardsListener cardsListener, String recipeCategory) {
    OneRecipeCard oneRecipeCard = new OneRecipeCard(recipeCategory, 2);
    Call<List<OneRecipeCard>> resp = cardAPI.getCardsSortedByCategoryQuery(oneRecipeCard);

    resp.enqueue(new Callback<List<OneRecipeCard>>() {
      @Override
      public void onResponse(Call<List<OneRecipeCard>> call, Response<List<OneRecipeCard>> response) {
        List<OneRecipeCard> recipes = response.body();
        cardsListener.setRecipesList(recipes);
      }
      @Override
      public void onFailure(Call<List<OneRecipeCard>> call, Throwable t) {
        Log.i("getCardsSortedByCategoryQuery.onFailure(): SERWER", t.getMessage());
      }
    });
  }

  @Override
  public void getUpdatedCard(final OnCardsListener cardsListener, int recipeId, final int position) {
    Call<List<OneRecipeCard>> resp = cardAPI.getUpdatedCard(recipeId);

    resp.enqueue(new Callback<List<OneRecipeCard>>() {
      @Override
      public void onResponse(Call<List<OneRecipeCard>> call, Response<List<OneRecipeCard>> response) {
        List<OneRecipeCard> recipe = response.body();
        cardsListener.setUpdatedCardFromServer(recipe.get(0), position);
      }
      @Override
      public void onFailure(Call<List<OneRecipeCard>> call, Throwable t) {
        Log.i("getUpdatedCard.onFailure(): SERWER", t.getMessage());
      }
    });
  }

}
