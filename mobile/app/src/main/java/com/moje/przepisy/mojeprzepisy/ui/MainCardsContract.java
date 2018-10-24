package com.moje.przepisy.mojeprzepisy.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface MainCardsContract {
  interface View {
    Context getContext();
    NavigationView getNavigationView();
    DrawerLayout getDrawerLayout();
    FloatingActionButton getFloatingActionButton();
    void setRecyclerView(List<OneRecipeCard> cardList);
    void setToolbar();
    void setNavigationViewListenerWithRegistration(NavigationView navigationView);
    void setNavigationViewListenerWithoutRegistration(NavigationView navigationView);
    void goToRecipeDetails(int recipeId);
    void setUpdatedCard(OneRecipeCard oneRecipeCard, int position);
  }

  interface Presenter {
    void sentStars(int recipeId, int starRate, int position);
    void sentHeart(int recipeId, int favorite, int position);
    void getAllCardsSortedFromServer(String method);
    void getSortedMethod(Context context);
    void setSortedMethod(Context context, String sortedMethod);
    void setDrawerLayoutListener(DrawerLayout drawerLayout);
    void setFloatingActionButton(FloatingActionButton floatingActionButton, boolean ifLogged);
    void getSearchedCardsFromServer(String recipeName);
    void setNavigationViewListener(NavigationView navigationView, boolean ifLogged);
    void onDestroy();
    void refreshCardsAction();
  }
}
