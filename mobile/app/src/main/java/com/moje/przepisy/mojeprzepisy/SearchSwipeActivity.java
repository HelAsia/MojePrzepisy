package com.moje.przepisy.mojeprzepisy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchSwipeActivity extends AppCompatActivity {
  FragmentPagerAdapter adapterViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_swipe);

    ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
    adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
    vpPager.setAdapter(adapterViewPager);
  }

  public static class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;

    public MyPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          CategorySearchFragment search1 = new CategorySearchFragment();
          return search1;
        case 1:
          IngredientsSearchFragment search2 = new IngredientsSearchFragment();
          return search2;
        case 2:
          DurationSearchFragment search3 = new DurationSearchFragment();
          return search3;
        case 3:
          AdditionalSearchFragment search4 = new AdditionalSearchFragment();
          return search4;
        case 4:
          ListSearchFragment search5 = new ListSearchFragment();
          return search5;
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return NUM_ITEMS;
    }
  }
}
