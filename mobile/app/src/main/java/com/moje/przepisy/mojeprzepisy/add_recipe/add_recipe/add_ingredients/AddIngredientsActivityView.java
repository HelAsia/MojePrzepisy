package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page.AddRecipeActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps.AddStepsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public class AddIngredientsActivityView extends AppCompatActivity implements AddIngredientsContract.View,
    View.OnClickListener {
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  private AddIngredientsContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddIngredientsPresenter(this);

    setListeners();
    setToolbar();
    presenter.setFirstScreen();
  }

  @Override
  public void setRecyclerView(List<Ingredient> ingredientList) {
    IngredientsAdapter adapter = new IngredientsAdapter(this, ingredientList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addIngredientsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setListeners() {
    addIngredientFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public List<Ingredient> setIngredientList(List<Ingredient> ingredientList) {
    return ingredientList;
  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.addIngredientFab){
      presenter.setNextStep();

    }else if(view.getId() == R.id.previousActionFab){
      presenter.addPojoListToFile();
      navigateToPreviousPage();

    }else if((view.getId() == R.id.nextActionFab)) {
      presenter.addPojoListToFile();
      navigateToNextPage();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_whole_recipe);
    toolbar.setSubtitle(R.string.add_recipe_title_step_two);
    setSupportActionBar(toolbar);
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent(AddIngredientsActivityView.this, AddRecipeActivityView.class);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent(AddIngredientsActivityView.this, AddStepsActivityView.class);
    startActivity(intent);
  }
}
