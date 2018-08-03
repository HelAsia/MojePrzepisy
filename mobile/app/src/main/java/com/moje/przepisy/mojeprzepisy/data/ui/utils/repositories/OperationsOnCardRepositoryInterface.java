package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface OperationsOnCardRepositoryInterface {

  interface OnCardsListener {

    void setRecipesList(List<OneRecipeCard> recipesList) ;

  }

  void getCardsSortedByChoseMethod(OnCardsListener cordsListener, String method);

  void getCardsSortedBySearchedQuery(OnCardsListener cardsListener, String recipeName);

}
