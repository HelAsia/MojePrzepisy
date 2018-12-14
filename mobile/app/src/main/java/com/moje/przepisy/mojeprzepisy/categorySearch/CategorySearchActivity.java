package com.moje.przepisy.mojeprzepisy.categorySearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategorySearchActivity extends AppCompatActivity implements
    CategorySearchAdapter.OnShareClickedListener {
  Context context;
  private Boolean isLogged;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_search);
    context = getApplicationContext();

    setToolbar();
    String[] categoryNameItems  = getResources().getStringArray(R.array.recipe_category_array);
    List<String> categoryNameList = new ArrayList<String>(Arrays.asList(categoryNameItems));
    setCategoryList(categoryNameList);
  }

  public void setCategoryList(List<String> categoryNameList){
    CategorySearchAdapter adapter = new CategorySearchAdapter(context, categoryNameList);
    RecyclerView recyclerView = findViewById(R.id.addCategoryNamesRecyclerView);
    int numberOfColumns = 2;
    adapter.setCategoryOnShareClickedListener(this);
    recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
    recyclerView.getLayoutManager().getPaddingRight();
    recyclerView.setAdapter(adapter);
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_category_name);
    toolbar.setSubtitle(R.string.category_search_title);
    setSupportActionBar(toolbar);
  }

  @Override
  public void ShareCategoryClicked() {
    Intent intent = new Intent(CategorySearchActivity.this, MainCardsActivity.class);
    intent.putExtra("LOGGED", getIsLogged());
    startActivity(intent);
  }

  public Boolean getIsLogged() {
    this.isLogged = getIntent().getExtras().getBoolean("LOGGED");
    return isLogged;
  }
}
