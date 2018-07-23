package com.moje.przepisy.mojeprzepisy.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.OperationsOnCardRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.List;

public class MainCardsPresenter implements MainCardsContract.Presenter,
    OperationsOnCardRepository.OnCardsListener {
  private OperationsOnCardRepository operationsOnCardRepository;
  private MainCardsContract.View cardsView;

  public MainCardsPresenter(MainCardsContract.View cardsView, OperationsOnCardRepository operationsOnCardRepository) {
    this.cardsView = cardsView;
    this.operationsOnCardRepository = operationsOnCardRepository;
  }

  public void getSortedMethod(Context context){
    String sortedMethodPref = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(Constant.PREF_SORTED_METHOD,"default");
    if(sortedMethodPref.equals("default")){
      getAllCardsFromServer();
    }else if(sortedMethodPref.equals("alphabetically")){
      getAllCardsSortedAlphabeticallyFromServer();
    }else if(sortedMethodPref.equals("lastAdded")){
      getAllCardsSortedByLastAddedFromServer();
    }else if(sortedMethodPref.equals("highestRated")){
      getAllCardsSortedByHighestRatedFromServer();
    }
  }
  @Override
  public void getAllCardsFromServer() {
    if(cardsView != null) {
      operationsOnCardRepository.getCards(this);
    }
  }

  @Override
  public void getAllCardsSortedAlphabeticallyFromServer() {
    if(cardsView != null) {
      operationsOnCardRepository.getCardsSortedAlphabetically(this);
    }
  }

  @Override
  public void getAllCardsSortedByLastAddedFromServer() {
    if(cardsView != null) {
      operationsOnCardRepository.getCardsSortedByLastAdded(this);
    }
  }

  @Override
  public void getAllCardsSortedByHighestRatedFromServer() {
    if(cardsView != null) {
      operationsOnCardRepository.getCardsSortedByHighestRated(this);
    }
  }

  @Override
  public void onDestroy() {
    cardsView = null;
  }

  @Override
  public void setRecipesList(List<OneRecipeCard> recipesList) {
    if(cardsView != null){
      cardsView.setRecyclerView(recipesList);
    }
  }

  @Override
  public void setNavigationViewListener(NavigationView navigationView, boolean ifLogged) {
    if(ifLogged) {
      cardsView.setNavigationViewListenerWithRegistriation(navigationView);
    }else {
      cardsView.setNavigationViewListenerWithoutRegistriation(navigationView);
    }
  }

  @Override
  public void setSortedMethod(Context context, String sortedMethod){
    SharedPreferences.Editor sortingSetting = PreferenceManager.getDefaultSharedPreferences(context).edit();
    sortingSetting.putString(Constant.PREF_SORTED_METHOD, sortedMethod).apply();
    sortingSetting.commit();
  }
}
