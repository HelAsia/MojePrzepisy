package com.moje.przepisy.mojeprzepisy.recipe_details.main_recipe_details;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.moje.przepisy.mojeprzepisy.R;

public class MainDetailsTabActivityView extends AppCompatActivity implements MainDetailsDisplayContract.View {
  MainDetailsDisplayContract.Presenter presenter;
  Context context;
  int recipeId;
  Boolean isLogged;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details_tab_view);
    context = getApplicationContext();

    presenter = new MainDetailsTabPresenter(this);

    presenter.setViewPager();
    presenter.setTabLayout();
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public MainDetailsFragmentPagerAdapter getMainDetailsFragmentPagerAdapter() {
    return new
        MainDetailsFragmentPagerAdapter(this, getSupportFragmentManager(), getRecipeId(), getIsLogged());
  }

  @Override
  public ViewPager getViewPager() {
    return (ViewPager) findViewById(R.id.viewpager);
  }

  @Override
  public TabLayout getTabLayout() {
    return (TabLayout) findViewById(R.id.sliding_tabs);
  }

  @Override
  public int getRecipeId() {
    this.recipeId = getIntent().getExtras().getInt("recipeId");
    return recipeId;
  }

  @Override
  public Boolean getIsLogged() {
    this.isLogged = getIntent().getExtras().getBoolean("isLogged");
    return isLogged;
  }
}
