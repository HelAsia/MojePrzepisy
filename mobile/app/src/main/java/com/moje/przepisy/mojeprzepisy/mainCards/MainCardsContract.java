package com.moje.przepisy.mojeprzepisy.mainCards;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import java.util.List;

public interface MainCardsContract {
  interface View {
    Context getContext();
    boolean getIsLoggedStatus();
    void setNavigationViewListener(boolean ifLogged);
    void setFloatingActionButton(boolean ifLogged);
    void setDrawerLayoutListener();
    void setRecyclerView(List<OneRecipeCard> cardList);
    void setToolbar();
    void setNavigationViewListenerWithRegistration(NavigationView navigationView);
    void setNavigationViewListenerWithoutRegistration(NavigationView navigationView);
    void goToRecipeDetails(int recipeId);
    void setUpdatedCard(OneRecipeCard oneRecipeCard, int position);
    void setErrorMessage(String message);
  }

  interface Presenter {
    String getSortedMethod(Context context);
    void sentStars(int recipeId, int starRate, int position);
    void sentHeart(int recipeId, int favorite, int position);
    void setSortedCards();
    void setFirstScreen();
    void setSortedMethod(Context context, String sortedMethod);
    void getSearchedCardsFromServer(String recipeName);
    void refreshCardsAction();
  }
}
