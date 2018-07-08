package com.moje.przepisy.mojeprzepisy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.HomePage;
import com.moje.przepisy.mojeprzepisy.LicensesActivity;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.SearchSwipeActivity;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.utils.MyCardViewAdapter;
import java.util.ArrayList;

public class MainCardsActivityView extends AppCompatActivity {
  private DrawerLayout drawerLayout;
  Context context;
  private RecyclerView recyclerView;
  private MyCardViewAdapter adapter;
  private ArrayList<OneRecipeCard> cardList = new ArrayList<>();
  private String[] recipePhotos;
  private String[] recipeName;
  private String[] recipeAuthor;
  private String[] starsCount;
  private String[] favoritesCount;
  private boolean ifLogged = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_cards);
    context = getApplicationContext();

    setToolbar();

    setDrawerLayoutListener();

    setNavigationViewListener();

    matchArraysWithResources();

    initCards();

    setRecyclerView();
  }

  public void setRecyclerView(){
    if(adapter == null) {
      adapter = new MyCardViewAdapter(this, cardList);
    }
    recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
  }

  public void setDrawerLayoutListener() {
    drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_cards);
    drawerLayout.addDrawerListener(
        new DrawerLayout.DrawerListener() {
          @Override
          public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
          }

          @Override
          public void onDrawerOpened(@NonNull View drawerView) {
          }

          @Override
          public void onDrawerClosed(@NonNull View drawerView) {
          }

          @Override
          public void onDrawerStateChanged(int newState) {
          }
        }
    );
  }

  public void setNavigationViewListener() {
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    ifLogged = getIntent().getExtras().getBoolean("LOGGED");
    if(ifLogged) {
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
                Intent intent = new Intent(MainCardsActivityView.this, HomePage.class);
                startActivity(intent);
              }
              return false;
            }
          }
      );
    }else {
      navigationView.getMenu().clear();
      navigationView.inflateMenu(R.menu.drawer_no_registered_menu);
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

              } else if (id == R.id.calculating_nav) {

              } else if (id == R.id.timer_nav) {

              } else if (id == R.id.licences_nav) {
                Intent intent = new Intent(MainCardsActivityView.this, LicensesActivity.class);
                startActivity(intent);
              }

              return false;
            }

          }
      );
    }
  }

  public void matchArraysWithResources() {
    recipePhotos = getResources().getStringArray(R.array.photo_list_array);
    recipeName = getResources().getStringArray(R.array.name_recipes_array);
    recipeAuthor = getResources().getStringArray(R.array.author_recipes_array);
    starsCount = getResources().getStringArray(R.array.stars_count_array);
    favoritesCount = getResources().getStringArray(R.array.favorites_count_array);
  }

  public int CardCount(){
    return recipeName.length;
  }

  public void initCards() {
    for (int i = 0; i < CardCount(); i++) {
      OneRecipeCard card = new OneRecipeCard();
      card.setId((long) i);
      card.setPhotoRecipe(recipePhotos[i]);
      card.setRecipeName(recipeName[i]);
      card.setAuthorName(recipeAuthor[i]);
      card.setStarsCount(starsCount[i]);
      card.setFavoritesCount(favoritesCount[i]);
      cardList.add(card);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sorting_registered_menu, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.sort_alphabetic:
        Toast.makeText(this, "Kliknięto 'Alfabetycznie'", Toast.LENGTH_SHORT).show();
        return true;
      case R.id.sort_last_add:
        Toast.makeText(this, "Kliknięto 'Ostatnio dodane'", Toast.LENGTH_SHORT).show();
        return true;
      case R.id.sort_highest_rated:
        Toast.makeText(this, "Kliknięto 'Najwyżej oceniane'", Toast.LENGTH_SHORT).show();
        return true;
      case R.id.favorites:
        Toast.makeText(this, "Kliknięto 'Ulubione'", Toast.LENGTH_SHORT).show();
        return true;
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}

