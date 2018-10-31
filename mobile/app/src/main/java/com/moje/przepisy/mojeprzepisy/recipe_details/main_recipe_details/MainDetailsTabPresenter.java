package com.moje.przepisy.mojeprzepisy.recipe_details.main_recipe_details;

public class MainDetailsTabPresenter implements MainDetailsDisplayContract.Presenter {
  private MainDetailsDisplayContract.View mainDetailsTabView;

  public MainDetailsTabPresenter(MainDetailsDisplayContract.View mainDetailsTabView) {
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
