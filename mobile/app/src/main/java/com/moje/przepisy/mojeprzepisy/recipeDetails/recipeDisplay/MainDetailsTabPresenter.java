package com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay;

public class MainDetailsTabPresenter implements MainDetailsDisplayContract.Presenter {
  private MainDetailsDisplayContract.View mainDetailsTabView;

  MainDetailsTabPresenter(MainDetailsDisplayContract.View mainDetailsTabView) {
    this.mainDetailsTabView = mainDetailsTabView;
  }

  @Override
  public void setFirstScreen() {
    mainDetailsTabView.setRecipeId();
    mainDetailsTabView.setIsLogged();
    mainDetailsTabView.setToolbar();
    mainDetailsTabView.setViewPager();
    mainDetailsTabView.setTabLayout();
  }
}
