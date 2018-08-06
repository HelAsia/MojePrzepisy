package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OperationsOnCardRepository implements OperationsOnCardRepositoryInterface {

  private Retrofit retrofit;
  private UserAPI userAPI;

  public OperationsOnCardRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void getCardsSortedByChoseMethod(final OnCardsListener cardsListener, String method) {
    Call<List<OneRecipeCard>> resp = userAPI.getCards();
    if (method.equals("default")){
      resp = userAPI.getCards();
    }else if (method.equals("alphabetically")){
      resp = userAPI.getCardsSortedAlphabetically();
    }else if (method.equals("lastAdded")){
      resp = userAPI.getCardsSortedByLastAdded();
    }else if (method.equals("highestRated")){
      resp = userAPI.getCardsSortedByHighestRated();
    }
    resp.enqueue(new Callback<List<OneRecipeCard>>() {
      @Override
      public void onResponse(Call<List<OneRecipeCard>> call, Response<List<OneRecipeCard>> response) {
        List<OneRecipeCard> recipes = response.body();
        cardsListener.setRecipesList(recipes);

       for (OneRecipeCard recipe : recipes) {
          Log.i("SERWER", "Id: " + recipe.id);
          Log.i("SERWER", "Author: " + recipe.authorName);
          Log.i("SERWER", "Favorite: " + recipe.favoritesCount);
          Log.i("SERWER", "PhotoRecipe: " + recipe.photoRecipe);
          Log.i("SERWER", "Recipe: " + recipe.recipeName);
          Log.i("SERWER", "Stars: " + recipe.starsCount);
        }
      }
      @Override
      public void onFailure(Call<List<OneRecipeCard>> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
      }
    });
  }

  @Override
  public void getCardsSortedBySearchedQuery(final OnCardsListener cardsListener, String recipeName) {
    OneRecipeCard oneRecipeCard = new OneRecipeCard(recipeName);
    Call<List<OneRecipeCard>> resp = userAPI.getCardsSortedBySearchedQuery(oneRecipeCard);

    resp.enqueue(new Callback<List<OneRecipeCard>>() {
      @Override
      public void onResponse(Call<List<OneRecipeCard>> call, Response<List<OneRecipeCard>> response) {
        List<OneRecipeCard> recipes = response.body();
        cardsListener.setRecipesList(recipes);
      }
      @Override
      public void onFailure(Call<List<OneRecipeCard>> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
      }
    });
  }
}
