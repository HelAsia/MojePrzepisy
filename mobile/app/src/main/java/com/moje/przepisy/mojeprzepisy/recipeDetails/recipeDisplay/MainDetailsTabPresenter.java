package com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay;

public class MainDetailsTabPresenter implements MainDetailsDisplayContract.Presenter {
  private MainDetailsDisplayContract.View mainDetailsTabView;

  MainDetailsTabPresenter(MainDetailsDisplayContract.View mainDetailsTabView) {
    this.mainDetailsTabView = mainDetailsTabView;
  }

  @Override
  public void setViewPager() {
    mainDetailsTabView.getViewPager().setAdapter(mainDetailsTabView.getMainDetailsFragmentPagerAdapter());
  }

  @Override
  public void setTabLayout() {
    mainDetailsTabView.getTabLayout().setupWithViewPager(mainDetailsTabView.getViewPager());
  }
}
