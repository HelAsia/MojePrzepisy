package com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public interface MainDetailsDisplayContract {
  interface View{
    Context getContext();
    MainDetailsFragmentPagerAdapter getMainDetailsFragmentPagerAdapter();
    ViewPager getViewPager();
    TabLayout getTabLayout();
    int getRecipeId();
    Boolean getIsLogged();
  }

  interface Presenter{
    void setViewPager();
    void setTabLayout();
  }

}
