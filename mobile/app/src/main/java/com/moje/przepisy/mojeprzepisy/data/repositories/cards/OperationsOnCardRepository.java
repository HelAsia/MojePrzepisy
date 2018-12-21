package com.moje.przepisy.mojeprzepisy.data.repositories.cards;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.network.CardAPI;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OperationsOnCardRepository implements OperationsOnCardRepositoryInterface {
  private CardAPI cardAPI;

  public OperationsOnCardRepository(Context context) {
    Retrofit retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.cardAPI = retrofit.create(CardAPI.class);
  }

  @Override
  public void getCardsSortedByChoseMethod(final OnCardsListener cardsListener, String method) {
    Single<List<OneRecipeCard>> resp;
    if (method.equals("alphabetically")){
      resp = cardAPI.getCardsSortedAlphabetically();
    }else if (method.equals("lastAdded")){
      resp = cardAPI.getCardsSortedByLastAdded();
    }else if (method.equals("highestRated")){
      resp = cardAPI.getCardsSortedByHighestRated();
    }else if (method.equals("favorite")){
      resp = cardAPI.getCardsSortedByFavorite();
    }else if (method.equals("myRecipe")){
      resp = cardAPI.getUserCards();
    }else{
      resp = cardAPI.getCards();
    }

    resp.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<OneRecipeCard>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onSuccess(List<OneRecipeCard> oneRecipeCards) {
                cardsListener.setRecipesList(oneRecipeCards);
              }

              @Override
              public void onError(Throwable e) {
                cardsListener.onError(e.getMessage());
              }
            });
  }

  @Override
  public void getCardsSortedBySearchedQuery(final OnCardsListener cardsListener, String recipeName) {
    OneRecipeCard oneRecipeCard = new OneRecipeCard(recipeName, 1);
    cardAPI.getCardsSortedBySearchedQuery(oneRecipeCard)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<OneRecipeCard>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onSuccess(List<OneRecipeCard> oneRecipeCards) {
                cardsListener.setRecipesList(oneRecipeCards);
              }

              @Override
              public void onError(Throwable e) {
                cardsListener.onError(e.getMessage());
              }
            });
  }

  @Override
  public void getCardsSortedByCategoryQuery(final OnCardsListener cardsListener, String recipeCategory) {
    OneRecipeCard oneRecipeCard = new OneRecipeCard(recipeCategory, 2);
    cardAPI.getCardsSortedByCategoryQuery(oneRecipeCard)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<OneRecipeCard>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onSuccess(List<OneRecipeCard> oneRecipeCards) {
                cardsListener.setRecipesList(oneRecipeCards);
              }

              @Override
              public void onError(Throwable e) {
                cardsListener.onError(e.getMessage());
              }
            });
  }

  @Override
  public void getUpdatedCard(final OnCardsListener cardsListener, int recipeId, final int position) {
    cardAPI.getUpdatedCard(recipeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<OneRecipeCard>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onSuccess(List<OneRecipeCard> oneRecipeCards) {
                cardsListener.setUpdatedCardFromServer(oneRecipeCards.get(0), position);
              }

              @Override
              public void onError(Throwable e) {
                cardsListener.onError(e.getMessage());
              }
            });
  }

}
