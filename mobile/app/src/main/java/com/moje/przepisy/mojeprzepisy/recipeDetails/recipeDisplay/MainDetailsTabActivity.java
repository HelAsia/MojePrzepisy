package com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.categorySearch.CategorySearchActivity;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDetailsTabActivity extends AppCompatActivity implements MainDetailsDisplayContract.View {
  @BindView(R.id.toolbar_recipe_details_tab) Toolbar toolbar;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.sliding_tabs) TabLayout tabLayout;
  private int recipeId;
  private Boolean isLogged = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details_tab_view);
    ButterKnife.bind(this);

    MainDetailsDisplayContract.Presenter presenter = new MainDetailsTabPresenter(this);

    presenter.setFirstScreen();
  }

  public void setIsLogged() {
    if(getIntent().getExtras() != null){
      isLogged = getIntent().getExtras().getBoolean("isLogged");
    }
  }

  public void setRecipeId() {
    if(getIntent().getExtras() != null){
      recipeId = getIntent().getExtras().getInt("id");
    }
  }

  public void setPosition() {
    if(getIntent().getExtras() != null){
      if(getIntent().getExtras().getInt("position") == 3) {
        viewPager.setCurrentItem(3);
      }
    }
  }

  //123456789

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public MainDetailsFragmentPagerAdapter getMainDetailsFragmentPagerAdapter() {
    return new
        MainDetailsFragmentPagerAdapter(this, getSupportFragmentManager(),
            recipeId, isLogged);
  }

  @Override
  public void setViewPager() {
    viewPager.setAdapter(getMainDetailsFragmentPagerAdapter());
  }

  @Override
  public void setTabLayout() {
    tabLayout.setupWithViewPager(viewPager);
  }

  public void setToolbar() {
    toolbar.setSubtitle(R.string.user_profile);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    if(actionbar != null){
      actionbar.setDisplayHomeAsUpEnabled(true);
      actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }
  }

  public boolean onOptionsItemSelected(MenuItem item){
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", isLogged);
    startActivity(intent);
    MainDetailsTabActivity.this.finish();
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", isLogged);
    startActivity(intent);
    MainDetailsTabActivity.this.finish();
  }
}
