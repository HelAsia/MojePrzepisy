package com.moje.przepisy.mojeprzepisy.ui;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface MainCardsContract {

  interface View {

    NavigationView getNavigationView();

    DrawerLayout getDrawerLayout();

    void setRecyclerView(List<OneRecipeCard> cardList);

    void setToolbar();

    void setNavigationViewListenerWithRegistriation(NavigationView navigationView);

    void setNavigationViewListenerWithoutRegistriation(NavigationView navigationView);
  }

  interface Presenter {

    void getAllCardsFromServer();

    void getAllCardsSortedAlphabeticallyFromServer();

    void getAllCardsSortedByLastAddedFromServer();

    void getAllCardsSortedByHighestRatedFromServer();

    void getSortedMethod(Context context);

    void setSortedMethod(Context context, String sortedMethod);

    void setDrawerLayoutListener(DrawerLayout drawerLayout);

    void getSearchedCardsFromServer(String recipeName);

    void setRecipesList(List<OneRecipeCard> cardList);

    void setNavigationViewListener(NavigationView navigationView, boolean ifLogged);

    void onDestroy();

  }

}
