package com.moje.przepisy.mojeprzepisy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainNoRegisteredActivity extends AppCompatActivity {
  private DrawerLayout mDrawerLayout;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_no_registered);

    context = getApplicationContext();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_no_registered);

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
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            int id = menuItem.getItemId();

            if (id == R.id.search_nav) {

            }else if (id == R.id.calculating_nav){

            }else if (id == R.id.timer_nav){

            }

            return false;
          }

        }
    );
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sorting_menu_no_rogistered, menu);
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
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
