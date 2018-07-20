package com.moje.przepisy.mojeprzepisy.ui;

import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.OperationsOnCardRepository;
import java.util.List;

public class MainCardsPresenter implements MainCardsContract.Presenter,
    OperationsOnCardRepository.OnCardsListener {
  private List<OneRecipeCard> recipesList;
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
  public void onDestroy() {
    cardsView = null;
  }


  @Override
  public void setRecipesList(List<OneRecipeCard> recipesList) {
    if(cardsView != null){
      cardsView.setRecyclerView(recipesList);

    }
  }

}
