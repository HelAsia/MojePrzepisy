package com.moje.przepisy.mojeprzepisy.ui;

import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface MainCardsContract {

  interface View {
    void setRecyclerView(List<OneRecipeCard> cardList);

  }

  interface Presenter {

    void getAllCardsFromServer();

    void setRecipesList(List<OneRecipeCard> cardList);

    void onDestroy();

  }

}
