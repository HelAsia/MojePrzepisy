package com.moje.przepisy.mojeprzepisy.addRecipe.displayRecipe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients.AddIngredientsActivity;
import com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo.AddRecipeActivity;
import com.moje.przepisy.mojeprzepisy.addRecipe.addSteps.AddStepsActivity;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import java.util.List;

public class DisplayRecipeActivity extends AppCompatActivity implements
    DisplayRecipeContract.View, OnClickListener{
  public static final int PLEASE_WAIT_DIALOG = 1;
  @BindView(R.id.saveRecipeImageView)ImageView saveRecipeImageView;
  @BindView(R.id.informationTextView)TextView informationTextView;
  @BindView(R.id.recipeEditImageView) ImageView recipeEditImageView;
  @BindView(R.id.ingredientsEditImageView) ImageView ingredientsEditImageView;
  @BindView(R.id.stepsEditImageView) ImageView stepsEditImageView;
  private DisplayRecipeContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_all_recipe_elements_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new DisplayRecipePresenter(this, new RecipeRepository(context));

    setToolbar();
    setOnClickListeners();
    presenter.setRecipeDetailsScreen();
    presenter.setIngredientsDetailScreen();
    presenter.setStepsDetailsScreen();
  }

  @Override
  public Dialog onCreateDialog(int dialogId) {
    switch (dialogId) {
      case PLEASE_WAIT_DIALOG:
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Zapisywanie");
        dialog.setMessage("Proszę czekać....");
        dialog.setCancelable(true);
        return dialog;

      default:
        break;
    }
    return null;
  }

  @Override
  public void setOnClickListeners() {
    saveRecipeImageView.setOnClickListener(this);
    recipeEditImageView.setOnClickListener(this);
    ingredientsEditImageView.setOnClickListener(this);
    stepsEditImageView.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.saveRecipeImageView){
      Log.i("onClick.saveRecipeImageView:", "Before startBackgroundActions()");
      presenter.startBackgroundActions(DisplayRecipeActivity.this);
      Log.i("onClick.saveRecipeImageView:", "After startBackgroundActions()");
    }else if(view.getId() == R.id.recipeEditImageView){
      presenter.setEditRecipeIconAction();
    }else if(view.getId() == R.id.ingredientsEditImageView){
      presenter.setEditIngredientsIconAction();
    }else if(view.getId() == R.id.stepsEditImageView){
      presenter.setEditStepsIconAction();
    }
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_whole_recipe);
    toolbar.setSubtitle("Krok 4: Sprawdź wprowdzone informacje");
    setSupportActionBar(toolbar);
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setRecipeRecyclerView(List<Recipe> recipeList) {
    DisplayMainInfoAdapter adapter = new DisplayMainInfoAdapter(this, recipeList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addMainRecipeInfoRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    DisplayIngredientsAdapter adapter = new DisplayIngredientsAdapter(this, ingredientList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addIngredientsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    DisplayStepsAdapter adapter = new DisplayStepsAdapter(this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addStepsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public TextView getInformationTextView() {
    return informationTextView;
  }

  @Override
  public void navigateToMainCardsScreen() {
    Intent intent = new Intent(DisplayRecipeActivity.this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
    DisplayRecipeActivity.this.finish();
  }

  @Override
  public void navigateToEditRecipeInformation() {
    Intent intent = new Intent(DisplayRecipeActivity.this, AddRecipeActivity.class);
    startActivity(intent);
  }

  @Override
  public void navigateToEditIngredients() {
    Intent intent = new Intent(DisplayRecipeActivity.this, AddIngredientsActivity.class);
    startActivity(intent);
  }

  @Override
  public void navigateToEditSteps() {
    Intent intent = new Intent(DisplayRecipeActivity.this, AddStepsActivity.class);
    startActivity(intent);
  }
}
