package com.moje.przepisy.mojeprzepisy.mainCards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.repositories.cards.OperationsOnCardRepository;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.List;

public class MainCardsPresenter implements MainCardsContract.Presenter,
    OperationsOnCardRepository.OnCardsListener, RecipeRepository.OnStarsEditListener{
  private OperationsOnCardRepository operationsOnCardRepository;
  private MainCardsContract.View cardsView;
  private RecipeRepository recipeRepository;

  public MainCardsPresenter(MainCardsContract.View cardsView, OperationsOnCardRepository operationsOnCardRepository, RecipeRepository recipeRepository) {
    this.cardsView = cardsView;
    this.operationsOnCardRepository = operationsOnCardRepository;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void getSortedMethod(Context context){
    String sortedMethodPref = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(Constant.PREF_SORTED_METHOD,"default");
    if(sortedMethodPref.equals("default")){
      getAllCardsSortedFromServer("default");
    }else if(sortedMethodPref.equals("alphabetically")){
      getAllCardsSortedFromServer("alphabetically");
    }else if(sortedMethodPref.equals("lastAdded")){
      getAllCardsSortedFromServer("lastAdded");
    }else if(sortedMethodPref.equals("highestRated")){
      getAllCardsSortedFromServer("highestRated");
    }else if(sortedMethodPref.equals("favorite")) {
      getAllCardsSortedFromServer("default");
    }else if (sortedMethodPref.equals("myRecipe")){
      getAllCardsSortedFromServer("default");
    }else {
      operationsOnCardRepository.getCardsSortedByCategoryQuery(this, sortedMethodPref);
    }
  }

  @Override
  public void sentStars(int recipeId, int starRate, int position) {
    recipeRepository.editStarsAndHeart(recipeId, "stars", starRate, this, position);
  }

  @Override
  public void sentHeart(int recipeId, int favorite, int position) {
    recipeRepository.editStarsAndHeart(recipeId, "favorite", favorite, this, position);
  }

  @Override
  public void getAllCardsSortedFromServer(String method) {
    if(cardsView != null) {
      if(method.equals("default")){
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "default");
      }else if(method.equals("alphabetically")){
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "alphabetically");
      }else if(method.equals("lastAdded")){
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "lastAdded");
      }else if(method.equals("highestRated")){
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "highestRated");
      }else if(method.equals("favorite")){
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "favorite");
      }else if(method.equals("myRecipe")){
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "myRecipe");
      }
    }
  }

  @Override
  public void getSearchedCardsFromServer(String recipeName) {
    if(cardsView != null) {
      operationsOnCardRepository.getCardsSortedBySearchedQuery(this, recipeName);
    }
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
      cardsView.setNavigationViewListenerWithRegistration(navigationView);
    }else {
      cardsView.setNavigationViewListenerWithoutRegistration(navigationView);
    }
  }

  @Override
  public void setFloatingActionButton(FloatingActionButton floatingActionButton, boolean ifLogged) {
    if(ifLogged) {
      cardsView.getFloatingActionButton().setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void setSortedMethod(Context context, String sortedMethod){
    SharedPreferences.Editor sortingSetting = PreferenceManager.getDefaultSharedPreferences(context).edit();
    sortingSetting.putString(Constant.PREF_SORTED_METHOD, sortedMethod).apply();
    sortingSetting.commit();
  }

  @Override
  public void setDrawerLayoutListener(DrawerLayout drawerLayout) {
    drawerLayout.addDrawerListener(
        new DrawerLayout.DrawerListener() {
          @Override
          public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
          }
          @Override
          public void onDrawerOpened(@NonNull View drawerView) {
          }
          @Override
          public void onDrawerClosed(@NonNull View drawerView) {
          }
          @Override
          public void onDrawerStateChanged(int newState) {
          }
        }
    );
  }

  @Override
  public void onDestroy() {
    cardsView = null;
  }

  @Override
  public void refreshCardsAction() {
    getSortedMethod(cardsView.getContext());
  }

  @Override
  public void setUpdatedCardFromServer(OneRecipeCard updatedCard, int position) {
    cardsView.setUpdatedCard(updatedCard, position);
  }

  @Override
  public void onUpdateStarsOrFavorite(int recipeId, int position) {
    operationsOnCardRepository.getUpdatedCard(this, recipeId, position);
  }
}

