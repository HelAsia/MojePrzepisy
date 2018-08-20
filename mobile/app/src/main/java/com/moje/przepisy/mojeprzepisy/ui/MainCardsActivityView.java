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
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.moje.przepisy.mojeprzepisy.LicensesActivity;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.SearchSwipeActivity;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page.AddRecipeActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.OperationsOnCardRepository;
import com.moje.przepisy.mojeprzepisy.log_in.LoginActivityView;
import com.moje.przepisy.mojeprzepisy.log_out.LogoutActivityView;
import com.moje.przepisy.mojeprzepisy.register.RegisterActivityView;
import java.util.List;

public class MainCardsActivityView extends AppCompatActivity implements MainCardsContract.View,
    View.OnClickListener {
  private MainCardsContract.Presenter presenter;
  private DrawerLayout drawerLayout;
  Context context;
  @BindView(R.id.my_fab) FloatingActionButton floatingActionButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_cards);
    context = getApplicationContext();

    presenter = new MainCardsPresenter(this,new OperationsOnCardRepository(context));
    presenter.getSortedMethod(context);
    setToolbar();
    presenter.setDrawerLayoutListener(getDrawerLayout());
    presenter.setNavigationViewListener(getNavigationView(), getIfLoggedStatus());
    presenter.setFloatingActionButton(getFloatingActionButton(), getIfLoggedStatus());

    floatingActionButton.setOnClickListener(this);

  }

  @Override
  public void setRecyclerView(List<OneRecipeCard> cardList){
    MyCardViewAdapter adapter = new MyCardViewAdapter(this, cardList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

  public boolean getIfLoggedStatus(){
    boolean ifLogged = getIntent().getExtras().getBoolean("LOGGED");
    return ifLogged;
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

            if (id == R.id.search_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, SearchSwipeActivity.class);
              startActivity(intent);

            }else if (id == R.id.add_nav) {

            }else if (id == R.id.calculating_nav){

            }else if (id == R.id.timer_nav){

            }else if (id == R.id.favorites_nav) {

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

            }else if (id == R.id.search_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, SearchSwipeActivity.class);
              startActivity(intent);

            }else if (id == R.id.calculating_nav) {

            }else if (id == R.id.timer_nav) {

            }else if (id == R.id.licences_nav) {
              Intent intent = new Intent(MainCardsActivityView.this, LicensesActivity.class);

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
        presenter.getAllCardsFromServer();
        item.setChecked(true);
        return true;

      case R.id.sort_alphabetic:
        presenter.setSortedMethod(context,"alphabetically");
        presenter.getAllCardsSortedAlphabeticallyFromServer();
        item.setChecked(true);
        Toast.makeText(this, "Sortowanie 'Alfabetycznie'", Toast.LENGTH_SHORT).show();
        return true;

      case R.id.sort_last_add:
        presenter.setSortedMethod(context,"lastAdded");
        presenter.getAllCardsSortedByLastAddedFromServer();
        item.setChecked(true);
        Toast.makeText(this, "Sortowanie 'Ostatnio dodane'", Toast.LENGTH_SHORT).show();
        return true;

      case R.id.sort_highest_rated:
        presenter.setSortedMethod(context,"highestRated");
        presenter.getAllCardsSortedByHighestRatedFromServer();
        item.setChecked(true);
        Toast.makeText(this, "Sortowanie 'Najwyżej oceniane'", Toast.LENGTH_SHORT).show();
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
}

