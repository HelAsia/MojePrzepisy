package com.moje.przepisy.mojeprzepisy.mainCards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

  MainCardsPresenter(MainCardsContract.View cardsView, OperationsOnCardRepository operationsOnCardRepository, RecipeRepository recipeRepository) {
    this.cardsView = cardsView;
    this.operationsOnCardRepository = operationsOnCardRepository;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public String getSortedMethod(Context context){
    return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(Constant.PREF_SORTED_METHOD,"default");
  }

  private String getCategory(Context context){
    return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(Constant.PREF_CATEGORY,"Zupy");
  }

  @Override
  public void setSortedCards(){
    String sortedMethodPref = getSortedMethod(cardsView.getContext());
    switch (sortedMethodPref){
      case "category":
        operationsOnCardRepository.getCardsSortedByCategoryQuery(this, getCategory(cardsView.getContext()));
        break;
      case "alphabetically":
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "alphabetically");
        break;
      case "lastAdded":
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "lastAdded");
        break;
      case "highestRated":
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "highestRated");
        break;
      case "favorite":
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "favorite");
        break;
      case "myRecipe":
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "myRecipe");
        break;
      default:
        operationsOnCardRepository.getCardsSortedByChoseMethod(this, "default");
        break;
    }
  }

  @Override
  public void setFirstScreen() {
    cardsView.setIsLoggedStatus();
    setSortedCards();
    cardsView.setToolbar();
    cardsView.setDrawerLayoutListener();
    cardsView.setNavigationViewListener(cardsView.getIsLoggedStatus());
    cardsView.setFloatingActionButton(cardsView.getIsLoggedStatus());
    cardsView.setFABListener();
    cardsView.setSwipeRefreshLayout();
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
  public void getSearchedCardsFromServer(String recipeName) {
    if(cardsView != null) {
      operationsOnCardRepository.getCardsSortedBySearchedQuery(this, recipeName);
    }
  }

  @Override
  public void setRecipesList(List<OneRecipeCard> recipesList) {
    if(cardsView != null){
      if(!recipesList.isEmpty()){
        cardsView.setErrorMessage("");
        cardsView.setRecyclerView(recipesList);
      }else{
        cardsView.setErrorMessage("Brak przepisów do wyświetlenia!");
      }
    }
  }

  @Override
  public void setSortedMethod(Context context, String sortedMethod){
    SharedPreferences.Editor sortingSetting = PreferenceManager.getDefaultSharedPreferences(context).edit();
    sortingSetting.putString(Constant.PREF_SORTED_METHOD, sortedMethod).apply();
    sortingSetting.commit();
  }

  @Override
  public void refreshCardsAction() {
    setSortedCards();
  }

  @Override
  public void setUpdatedCardFromServer(OneRecipeCard updatedCard, int position) {
    cardsView.setUpdatedCard(updatedCard, position);
  }

  @Override
  public void onError(String message) {
    cardsView.setErrorMessage(message);
  }

  @Override
  public void setMaxDateInPreferences(int maxDate) {
    SharedPreferences.Editor preferences =
            PreferenceManager.getDefaultSharedPreferences(cardsView.getContext()).edit();
    preferences.putInt(Constant.PREF_MAX_DATE, maxDate).apply();
    preferences.commit();
  }

  @Override
  public void onUpdateStarsOrFavorite(int recipeId, int position) {
    operationsOnCardRepository.getUpdatedCard(this, recipeId, position);
  }
}

