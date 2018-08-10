package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page.AddRecipeActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps.AddStepsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddIngredientsActivityView extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  String backgroundColorString = "#CCFFCC";
  int[] layoutElementsArray = new int[6];
  List<ImageView> imageViewList = new ArrayList<>();
  List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();
  Random randomNumber = new Random();
  LinearLayout linearLayoutOneIngredient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    ButterKnife.bind(this);

    linearLayoutOneIngredient = (LinearLayout) findViewById(
        R.id.addIngredientsLayout);

    addIngredientFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();
  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.addIngredientFab){
      View child = getLayoutInflater().inflate(R.layout.one_ingredient_layout, null);
      child.setId(generateViewId());

      setBackground(child);
      linearLayoutOneIngredient.addView(child);
      getElementsIdToArray(child);

      IngredientElementsId layoutForIngredient = new IngredientElementsId(child.getId(), this.layoutElementsArray[0],
          this.layoutElementsArray[1], this.layoutElementsArray[2], this.layoutElementsArray[3], this.layoutElementsArray[4], this.layoutElementsArray[5]);

      ingredientElementsIdList.add(layoutForIngredient);
      setDeleteImageViews(ingredientElementsIdList);
      setDeleteImageViewListener();

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();
    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();
    }else {
      View myViewToRemove = findViewById(view.getId());

      int position = checkPositionOfLayoutToRemove(view.getId(), ingredientElementsIdList);

      ViewGroup firstLineLayoutParentViewToRemove = (ViewGroup) myViewToRemove.getParent();
      ViewGroup firstAndSecondLineLayoutParentParentViewToRemove = (ViewGroup) firstLineLayoutParentViewToRemove.getParent();

      linearLayoutOneIngredient.removeView(firstAndSecondLineLayoutParentParentViewToRemove);
      ingredientElementsIdList.remove(position);

      setIngredientBackgroundAfterDelete();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_ingredient);
    toolbar.setSubtitle(R.string.add_recipe_title_step_two);
    setSupportActionBar(toolbar);
  }

  public int checkPositionOfLayoutToRemove(int elementId, List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      if (ingredientElementsIdList.get(i).getDeleteImageViewId() == elementId){
        return i;
      }
    }
    return -1;
  }

  public void setBackground(View child){
    if(backgroundColorString.equals("#ffffff")){
      this.backgroundColorString = "#CCFFCC";
    }else if(backgroundColorString.equals("#CCFFCC")){
      this.backgroundColorString = "#ffffff";
    }
    child.setBackgroundColor(Color.parseColor(backgroundColorString));
  }

  public void setIngredientBackgroundAfterDelete(){
    for(int i = 0; i < linearLayoutOneIngredient.getChildCount(); i++){
      View linearLayoutOneIngredientView = linearLayoutOneIngredient.getChildAt(i);
      if(i % 2 == 0) {
        this.backgroundColorString = "#ffffff";
      }else if(i % 2 == 1){
        this.backgroundColorString = "#CCFFCC";
      }
      linearLayoutOneIngredientView.setBackgroundColor(Color.parseColor( backgroundColorString));
    }
  }

  public void getElementsIdToArray(View child){
    ViewGroup childElementsView = (ViewGroup) findViewById(child.getId());
    for(int i = 0; i < childElementsView.getChildCount(); i++){
      View childOneElementView = childElementsView.getChildAt(i);
      childOneElementView.setId(generateViewId());
      int childId = childOneElementView.getId();
      if (i == 0){
        this.layoutElementsArray[0] = childId;
        ViewGroup insideChildElementView = (ViewGroup) findViewById(childId);
        for(int j = 0; j < insideChildElementView.getChildCount(); j++){
          View insideChildOneElementView = insideChildElementView.getChildAt(j);
          insideChildOneElementView.setId(generateViewId());
          int insideChildId = insideChildOneElementView.getId();
          this.layoutElementsArray[j+1] = insideChildId;
        }
      }if (i == 1){
        this.layoutElementsArray[5] = childId;
      }
    }
  }

  public void setDeleteImageViews(List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      imageViewList.add((ImageView) findViewById(ingredientElementsIdList.get(i).getDeleteImageViewId()));
    }
  }

  public void setDeleteImageViewListener(){
    for(ImageView oneImageView : imageViewList){
      oneImageView.setOnClickListener(this);
    }
  }

  public int generateViewId(){
     return randomNumber.nextInt((1000000000 - 100 + 1) + 100);
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
