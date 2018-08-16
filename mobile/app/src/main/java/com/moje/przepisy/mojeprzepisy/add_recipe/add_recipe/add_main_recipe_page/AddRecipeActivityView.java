package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps.AddStepsActivityView;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;
import com.moje.przepisy.mojeprzepisy.utils.TimeSetDialog;

public class AddRecipeActivityView extends AppCompatActivity implements AddRecipeContract.View,
    View.OnClickListener {
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  @BindView(R.id.preparedTimeEditText) TextView preparedTimeEditText;
  @BindView(R.id.cookTimeEditText) TextView cookTimeEditText;
  @BindView(R.id.bakeTimeEditText) TextView bakeTimeEditText;
  TimeSetDialog timeSetDialog = new TimeSetDialog();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_recipe);
    ButterKnife.bind(this);

    nextActionFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    preparedTimeEditText.setOnClickListener(this);
    cookTimeEditText.setOnClickListener(this);
    bakeTimeEditText.setOnClickListener(this);

    setToolbar();
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();
    }else if(view.getId() == R.id.nextActionFab){
      navigateToNextPage();
    }else if(view.getId() == R.id.preparedTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivityView.this, preparedTimeEditText);
    }else if(view.getId() == R.id.cookTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivityView.this, cookTimeEditText);
    }else if(view.getId() == R.id.bakeTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivityView.this, bakeTimeEditText);
    }
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
