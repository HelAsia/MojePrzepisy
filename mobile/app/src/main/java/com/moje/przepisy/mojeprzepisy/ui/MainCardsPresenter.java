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

  public void setSortedType(Context context, String sortedtype){
    SharedPreferences.Editor sortingSetting = PreferenceManager.getDefaultSharedPreferences(context).edit();
    sortingSetting.putString(Constant.PREF_SORTED_TYPE, sortedtype).apply();
    sortingSetting.commit();
  }
}
