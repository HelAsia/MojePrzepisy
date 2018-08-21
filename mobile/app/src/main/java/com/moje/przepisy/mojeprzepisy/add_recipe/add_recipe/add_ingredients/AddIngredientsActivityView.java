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
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.ArrayList;
import java.util.List;

public class AddIngredientsActivityView extends AppCompatActivity implements AddIngredientsContract.View,
    View.OnClickListener {
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  List<Ingredient> ingredientList = new ArrayList<>();
  private AddIngredientsContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddIngredientsPresenter(this, new RecipeRepository(context));

    addIngredientFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();

    ingredientList = presenter.getIngredientListAfterChangeScreen(presenter.getPojoListFromPreferences(context));

    if(ingredientList != null){
      presenter.setIngredientList(ingredientList);
      setRecyclerView(presenter.getIngredientList());
    }else {
      Ingredient emptyIngredient = new Ingredient(1, 1, "np. cukier");
      presenter.getIngredientList().add(emptyIngredient);
      presenter.setIngredientList(presenter.getIngredientList());
      setRecyclerView(presenter.getIngredientList());
    }
  }

  @Override
  public void setRecyclerView(List<Ingredient> ingredientList) {
    IngredientsAdapter adapter = new IngredientsAdapter(this, ingredientList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addIngredientsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }


  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.addIngredientFab){

      Ingredient emptyIngredient = new Ingredient(1, 1, "np. cukier");

      presenter.getIngredientList().add(emptyIngredient);
      presenter.setIngredientList(ingredientList);
      setRecyclerView(presenter.getIngredientList());

      String pojoToJson = presenter.convertPojoToJsonString(presenter.getIngredientList());
      presenter.addPojoListToPreferences(pojoToJson, context);

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();

    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_ingredient);
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