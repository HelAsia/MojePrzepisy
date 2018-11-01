package com.moje.przepisy.mojeprzepisy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page.AddRecipeActivityView;
import com.moje.przepisy.mojeprzepisy.category_search.CategorySearchActivity;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.cards.OperationsOnCardRepository;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.licenses.LicensesActivity;
import com.moje.przepisy.mojeprzepisy.log_in.LoginActivityView;
import com.moje.przepisy.mojeprzepisy.log_out.LogoutActivityView;
import com.moje.przepisy.mojeprzepisy.recipe_details.main_recipe_details.MainDetailsTabActivityView;
import com.moje.przepisy.mojeprzepisy.register.RegisterActivityView;
import com.moje.przepisy.mojeprzepisy.timer.TimerActivityView;
import com.moje.przepisy.mojeprzepisy.ui.MyCardViewAdapter.OnShareHeartClickedListener;
import com.moje.przepisy.mojeprzepisy.ui.MyCardViewAdapter.OnShareStarsClickedListener;
import com.moje.przepisy.mojeprzepisy.user_profile.UserProfileActivityView;
import java.util.List;

public class MainCardsActivityView extends AppCompatActivity implements MainCardsContract.View,
    View.OnClickListener,OnShareStarsClickedListener, OnShareHeartClickedListener,
    MyCardViewAdapter.OnShareRecipeIdClickedListener{
  @BindView(R.id.my_fab) FloatingActionButton floatingActionButton;
  private MainCardsContract.Presenter presenter;
  private DrawerLayout drawerLayout;
  Context context;
  MyCardViewAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_cards);
    context = getApplicationContext();

    presenter = new MainCardsPresenter(this, new OperationsOnCardRepository(context), new RecipeRepository(context));
    presenter.getSortedMethod(context);
    setToolbar();
    presenter.setDrawerLayoutListener(getDrawerLayout());
    presenter.setNavigationViewListener(getNavigationView(), getIsLoggedStatus());
    presenter.setFloatingActionButton(getFloatingActionButton(), getIsLoggedStatus());

    floatingActionButton.setOnClickListener(this);
  }

  @Override
  public void shareRecipeIdClicked(int recipeId) {
    goToRecipeDetails(recipeId);
  }

  public Context getContext() {
    return context;
  }

  @Override
  protected void onRestart(){
    super.onRestart();
    presenter.setSortedMethod(context,"default");
  }

  @Override
  protected void onResume(){
    super.onResume();
    presenter.setSortedMethod(context,"default");
  }

  @Override
  public void setRecyclerView(List<OneRecipeCard> cardList){
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    setAdapter(cardList);
    MyCardViewAdapter adapter = getAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter.setStarsOnShareClickedListener(this);
    adapter.setHeartOnShareClickedListener(this);
    adapter.setCallbackRecipeIdOnShareClickedListener(this);
  }

  public void setAdapter(List<OneRecipeCard> cardList){
    this.adapter = new MyCardViewAdapter(this, cardList);
  }

  public MyCardViewAdapter getAdapter(){
    return adapter;
  }

  @Override
  public FloatingActionButton getFloatingActionButton(){
    floatingActionButton = (FloatingActionButton) findViewById(R.id.my_fab);
    return floatingActionButton;
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_recipe);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
  }

  @Override
  public DrawerLayout getDrawerLayout() {
    drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_cards);
    return drawerLayout;
  }

  public NavigationView getNavigationView(){
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    return navigationView;
  }

  public boolean getIsLoggedStatus(){
    boolean isLogged = getIntent().getExtras().getBoolean("LOGGED");
    return isLogged;
  }

  @Override
  public void setNavigationViewListenerWithRegistration(NavigationView navigationView) {
    navigationView.getMenu().clear();
    navigationView.inflateMenu(R.menu.drawer_registered_menu);
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            int id = menuItem.getItemId();

            if (id == R.id.category_nav){
              Intent intent = new Intent(MainCardsActivityView.this, CategorySearchActivity.class);
              intent.putExtra("LOGGED",true);
              startActivity(intent);
            }else if (id == R.id.add_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, AddRecipeActivityView.class);
              startActivity(intent);
            }else if (id == R.id.timer_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, TimerActivityView.class);
              startActivity(intent);
            }else if(id == R.id.my_profile_nav){
              Intent intent = new Intent(MainCardsActivityView.this, UserProfileActivityView.class);
              startActivity(intent);
            }else if (id == R.id.my_recipe_nav) {
              presenter.setSortedMethod(context,"myRecipe");
              presenter.getAllCardsSortedFromServer("myRecipe");
            }else if (id == R.id.favorites_nav) {
              presenter.setSortedMethod(context,"favorite");
              presenter.getAllCardsSortedFromServer("favorite");
            }else if (id == R.id.licences_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, LicensesActivity.class);
              startActivity(intent);
            }else if (id == R.id.logout_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, LogoutActivityView.class);
              startActivity(intent);
            }
            return false;
          }
        }
    );
  }

  @Override
  public void setNavigationViewListenerWithoutRegistration(NavigationView navigationView) {
    navigationView.getMenu().clear();
    navigationView.inflateMenu(R.menu.drawer_no_registered_menu);
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            int id = menuItem.getItemId();

            if( id == R.id.register_nav){
              Intent intent = new Intent(MainCardsActivityView.this, RegisterActivityView.class);
              startActivity(intent);
            }else if( id == R.id.login_nav){
              Intent intent = new Intent(MainCardsActivityView.this, LoginActivityView.class);
              startActivity(intent);
            }else if(id == R.id.category_nav){
              Intent intent = new Intent(MainCardsActivityView.this, CategorySearchActivity.class);
              intent.putExtra("LOGGED",false);
              startActivity(intent);
            }else if (id == R.id.timer_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, TimerActivityView.class);
              startActivity(intent);
            }else if (id == R.id.licences_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, LicensesActivity.class);
              startActivity(intent);
            }
            return false;
          }
        }
    );
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sorting_menu, menu);

    MenuItem refreshViewItem = menu.findItem(R.id.action_refresh);
    refreshViewItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem menuItem) {
        presenter.refreshCardsAction();
        return true;
      }
    });

    MenuItem searchViewItem = menu.findItem(R.id.action_search);
    final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
    searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        presenter.getSearchedCardsFromServer(query);
        Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    return super.onCreateOptionsMenu(menu);
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.sort_default:
        presenter.setSortedMethod(context,"default");
        presenter.getAllCardsSortedFromServer("default");
        item.setChecked(true);
        return true;

      case R.id.sort_alphabetic:
        presenter.setSortedMethod(context,"alphabetically");
        presenter.getAllCardsSortedFromServer("alphabetically");
        item.setChecked(true);
        return true;

      case R.id.sort_last_add:
        presenter.setSortedMethod(context,"lastAdded");
        presenter.getAllCardsSortedFromServer("lastAdded");
        item.setChecked(true);
        return true;

      case R.id.sort_highest_rated:
        presenter.setSortedMethod(context,"highestRated");
        presenter.getAllCardsSortedFromServer("highestRated");
        item.setChecked(true);
        return true;

      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onClick(View view) {
    Intent intent = new Intent(MainCardsActivityView.this, AddRecipeActivityView.class);
    startActivity(intent);
  }

  @Override
  public void shareStarsClicked(int recipeId, int starRate, int position) {
    presenter.sentStars(recipeId, starRate, position);
  }

  @Override
  public void shareHeartClicked(int recipeId, int favorite, int position) {
    presenter.sentHeart(recipeId, favorite, position);
  }

  @Override
  public void goToRecipeDetails(int recipeId){
    Intent intent = new Intent(MainCardsActivityView.this, MainDetailsTabActivityView.class);
    Bundle extras = new Bundle();
    extras.putInt("recipeId", recipeId);
    extras.putBoolean("isLogged", getIsLoggedStatus());
    intent.putExtras(extras);
    startActivity(intent);
  }

  @Override
  public void setUpdatedCard(OneRecipeCard oneRecipeCard, int position){
    getAdapter().updateFavoriteOnCard(oneRecipeCard, position);
  }
}

