package com.moje.przepisy.mojeprzepisy.ui;

import android.content.Context;
import android.support.design.widget.NavigationView;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface MainCardsContract {

  interface View {
    void setRecyclerView(List<OneRecipeCard> cardList);

    void setToolbar();

    void setDrawerLayoutListener();

    void setNavigationViewListenerWithRegistriation(NavigationView navigationView);

    void setNavigationViewListenerWithoutRegistriation(NavigationView navigationView);
  }

  interface Presenter {

    void getAllCardsFromServer();

    void getAllCardsSortedAlphabeticallyFromServer();

    void getAllCardsSortedByLastAddedFromServer();

    void getAllCardsSortedByHighestRatedFromServer();

    void setSortedMethod(Context context, String sortedMethod);

    void getSortedMethod(Context context);

    void setRecipesList(List<OneRecipeCard> cardList);

    void setNavigationViewListener(NavigationView navigationView, boolean ifLogged);

    void onDestroy();

  }

}
