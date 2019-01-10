package com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public interface MainDetailsDisplayContract {
  interface View{
    Context getContext();
    MainDetailsFragmentPagerAdapter getMainDetailsFragmentPagerAdapter();
    void setToolbar();
    void setViewPager();
    void setTabLayout();
    void setRecipeId();
    void setIsLogged();
  }

  interface Presenter{
    void setFirstScreen();
  }
}
