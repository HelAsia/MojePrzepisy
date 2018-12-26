package com.moje.przepisy.mojeprzepisy.categorySearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CategorySearchActivity extends AppCompatActivity implements
    CategorySearchAdapter.OnShareClickedListener {
  @BindView(R.id.toolbar_category_name) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_search);

    setToolbar();
    setCategoryList(getCategoryNameList());
  }

  private List<String> getCategoryNameList(){
    String[] categoryNameItems  = getResources().getStringArray(R.array.recipe_category_array);
    return new ArrayList<>(Arrays.asList(categoryNameItems));
  }

  public void setCategoryList(List<String> categoryNameList){
    CategorySearchAdapter adapter = new CategorySearchAdapter(this, categoryNameList);
    RecyclerView recyclerView = findViewById(R.id.addCategoryNamesRecyclerView);
    int numberOfColumns = 2;
    adapter.setCategoryOnShareClickedListener(this);
    recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
    recyclerView.getLayoutManager().getPaddingRight();
    recyclerView.setAdapter(adapter);
  }

  public void setToolbar() {
    toolbar.setSubtitle(R.string.category_search_title);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    if(actionbar != null){
      actionbar.setDisplayHomeAsUpEnabled(true);
      actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }
  }

  @Override
  public void ShareCategoryClicked() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("LOGGED", getIsLogged());
    startActivity(intent);
    CategorySearchActivity.this.finish();
  }

  public Boolean getIsLogged() {
    return getIntent().getExtras().getBoolean("LOGGED");
  }
}
