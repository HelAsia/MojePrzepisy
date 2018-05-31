package com.moje.przepisy.mojeprzepisy;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class MainRegisteredActivity extends AppCompatActivity {
  private DrawerLayout mDrawerLayout;
  Context context;
  private RecyclerView recyclerView;
  private MyCardViewAdapter adapter;
  private ArrayList<OneRecipeCard> cardList = new ArrayList<>();
  private TypedArray recipePhotos;
  private String[] recipeName;
  private String[] recipeAuthor;
  private String[] starsCount;
  private String[] favoritesCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_registered);

    context = getApplicationContext();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_registered);

    mDrawerLayout.addDrawerListener(
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

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
    navMenuView.addItemDecoration(new DividerItemDecoration(MainRegisteredActivity.this,DividerItemDecoration.VERTICAL));
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            int id = menuItem.getItemId();

            if (id == R.id.search_nav) {

            }else if (id == R.id.add_nav) {

            }else if (id == R.id.calculating_nav){

            }else if (id == R.id.timer_nav){

            }else if (id == R.id.favorites_nav) {

            }else if (id == R.id.licences_nav) {
              Intent intent = new Intent(MainRegisteredActivity.this, LicensesActivity.class);
              startActivity(intent);
            }
            return false;
          }
        }
    );

    recipePhotos = getResources().obtainTypedArray(R.array.photo_list);
    recipeName = getResources().getStringArray(R.array.name_recipes_array);
    recipeAuthor = getResources().getStringArray(R.array.author_recipes_array);
    starsCount = getResources().getStringArray(R.array.stars_count_array);
    favoritesCount = getResources().getStringArray(R.array.favorites_count_array);

    initCards();

    if(adapter == null) {
      adapter = new MyCardViewAdapter(this, cardList);
    }
    recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

  }

  public void doSmoothScroll(int position) {
    recyclerView.smoothScrollToPosition(position);
  }

  public void initCards() {
    for (int i = 0; i < 9; i++) {
      OneRecipeCard card = new OneRecipeCard();
      card.setId((long) i);
      card.setPhotoRecipe(
          BitmapFactory.decodeResource(getResources(),recipePhotos.getResourceId(i, -1)));
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
    inflater.inflate(R.menu.sorting_menu_registered, menu);
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
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
