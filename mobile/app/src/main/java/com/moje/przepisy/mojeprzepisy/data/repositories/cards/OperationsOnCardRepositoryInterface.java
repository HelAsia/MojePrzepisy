package com.moje.przepisy.mojeprzepisy.data.repositories.cards;

import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface OperationsOnCardRepositoryInterface {
  interface OnCardsListener {
    void setRecipesList(List<OneRecipeCard> recipesList) ;
    void setUpdatedCardFromServer(OneRecipeCard updatedCard, int position);
    void onError(String message);
    void setMaxDateInPreferences(int maxDate);
  }

  interface OnNewCardsListener {
    void setNewCardsNotification();
  }

  void getCardsSortedByChoseMethod(OnCardsListener cordsListener, String method);
  void getCardsSortedBySearchedQuery(OnCardsListener cardsListener, String recipeName);
  void getCardsSortedByCategoryQuery(OnCardsListener cardsListener, String recipeCategory);
  void getUpdatedCard(OnCardsListener cardsListener, int recipeId, int position);
  void getNewCards(OnNewCardsListener newCardsListener, int maxDate);
}
