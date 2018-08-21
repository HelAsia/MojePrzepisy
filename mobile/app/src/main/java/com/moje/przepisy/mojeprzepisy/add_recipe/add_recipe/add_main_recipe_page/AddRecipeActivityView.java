package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;
import com.moje.przepisy.mojeprzepisy.utils.TimeSetDialog;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class AddRecipeActivityView extends AppCompatActivity implements AddRecipeContract.View,
    View.OnClickListener {
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  @BindView(R.id.recipeNameEditText) EditText recipeNameEditText;
  @BindView(R.id.mainPhotoImageView) ImageView mainPhotoImageView;
  @BindView(R.id.categoryChooseSpinner) Spinner categoryChooseSpinner;
  @BindView(R.id.preparedTimeEditText) TextView preparedTimeEditText;
  @BindView(R.id.cookTimeEditText) TextView cookTimeEditText;
  @BindView(R.id.bakeTimeEditText) TextView bakeTimeEditText;
  private AddRecipeContract.Presenter presenter;
  TimeSetDialog timeSetDialog = new TimeSetDialog();
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_recipe);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new AddRecipePresenter(this, new RecipeRepository(context));

    nextActionFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    preparedTimeEditText.setOnClickListener(this);
    cookTimeEditText.setOnClickListener(this);
    bakeTimeEditText.setOnClickListener(this);

    setToolbar();

    Recipe recipe = presenter.getRecipeAfterChangeScreen(presenter.getPojoFromPreferences(context));
    if(recipe != null){
      presenter.setRecipeValueOnScreen();
    }
  }

  public Context getContext(){
    return context;
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.previousActionFab){
      setRecipeValueInPreferences();
      navigateToPreviousPage();
    }else if(view.getId() == R.id.nextActionFab){
      setRecipeValueInPreferences();
      navigateToNextPage();
    }else if(view.getId() == R.id.preparedTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivityView.this, preparedTimeEditText);
    }else if(view.getId() == R.id.cookTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivityView.this, cookTimeEditText);
    }else if(view.getId() == R.id.bakeTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivityView.this, bakeTimeEditText);
    }
  }

  public void setRecipeValueInPreferences(){
    presenter.getRecipe().setRecipeName(recipeNameEditText.getText().toString());
    presenter.getRecipe().setRecipeMainPictureId(mainPhotoImageView.getDrawable().toString());
    presenter.getRecipe().setRecipeCategory((int)categoryChooseSpinner.getSelectedItemId());
    presenter.getRecipe().setRecipePrepareTime(java.sql.Time.valueOf(preparedTimeEditText.getText().toString()));
    presenter.getRecipe().setRecipeCookTime(java.sql.Time.valueOf(cookTimeEditText.getText().toString()));
    presenter.getRecipe().setRecipeBakeTime(java.sql.Time.valueOf(bakeTimeEditText.getText().toString()));

    String pojoJson = presenter.convertPojoToJsonString(presenter.getRecipe());
    presenter.addPojoToPreferences(pojoJson, context);
  }

  public void setRecipeNameEditText(String recipeName){
    recipeNameEditText.setText(recipeName);
  }

  public void setMainPhotoImageView(){

  }

  public void setCategoryChooseSpinner(int position){
    categoryChooseSpinner.setSelection(position);
  }

  public void setPreparedTimeEditText(java.sql.Time time){
    preparedTimeEditText.setText(time.toString());
  }

  public void setCookTimeEditText(java.sql.Time time){
    cookTimeEditText.setText(time.toString());
  }

  public void setBakeTimeEditText(java.sql.Time time){
    bakeTimeEditText.setText(time.toString());
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_recipe);
    toolbar.setSubtitle(R.string.add_recipe_title_step_one);
    setSupportActionBar(toolbar);
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent (AddRecipeActivityView.this, MainCardsActivityView.class);
    intent.putExtra("LOGGED",true);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent (AddRecipeActivityView.this, AddIngredientsActivityView.class);
    startActivity(intent);
  }
}
