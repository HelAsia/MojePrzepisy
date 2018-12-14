package com.moje.przepisy.mojeprzepisy.data.repositories.cards;

import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface OperationsOnCardRepositoryInterface {
  interface OnCardsListener {
    void setRecipesList(List<OneRecipeCard> recipesList) ;
    void setUpdatedCardFromServer(OneRecipeCard updatedCard, int position);
  }

  void getCardsSortedByChoseMethod(OnCardsListener cordsListener, String method);
  void getCardsSortedBySearchedQuery(OnCardsListener cardsListener, String recipeName);
  void getCardsSortedByCategoryQuery(OnCardsListener cardsListener, String recipeCategory);
  void getUpdatedCard(OnCardsListener cardsListener, int recipeId, int position);
}
