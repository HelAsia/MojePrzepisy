package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

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
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements.DisplayAllRecipeElementsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddStepsActivityView extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.addStepFab) FloatingActionButton addStepFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  String backgroundColorString = "#CCFFCC";
  int[] layoutElementsArray = new int[12];
  List<ImageView> deleteImageViewsList = new ArrayList<>();
  List<StepElementsId> stepElementsIdList = new ArrayList<>();
  Random randomNumber = new Random();
  LinearLayout linearLayoutOneStep;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_steps_view);
    ButterKnife.bind(this);

    linearLayoutOneStep = (LinearLayout) findViewById(
        R.id.addsStepsLayout);


    for(int i = 0 ; i < stepElementsIdList.size(); i++){
      View child = findViewById(stepElementsIdList.get(i).getLayoutId());
      linearLayoutOneStep.addView(child);
    }

    addStepFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.addStepFab){
      View child = getLayoutInflater().inflate(R.layout.one_step_layout, null);
      child.setId(generateViewId());

      setBackground(child);
      linearLayoutOneStep.addView(child);
      getElementsIdToArray(child);

      StepElementsId layoutForSteps = new StepElementsId(child.getId(), this.layoutElementsArray[0],
          this.layoutElementsArray[1], this.layoutElementsArray[2], this.layoutElementsArray[3], this.layoutElementsArray[4],
          this.layoutElementsArray[5], this.layoutElementsArray[6], this.layoutElementsArray[7], this.layoutElementsArray[8],
          this.layoutElementsArray[9], this.layoutElementsArray[10], this.layoutElementsArray[11]);

      stepElementsIdList.add(layoutForSteps);
      setDeleteImageViews(stepElementsIdList);
      setDeleteImageViewListener();

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();
    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();
    }else {
      View myViewToRemove = findViewById(view.getId());

      int position = checkPositionOfLayoutToRemove(view.getId(), stepElementsIdList);

      ViewGroup firstLineLayoutParentViewToRemove = (ViewGroup) myViewToRemove.getParent();
      ViewGroup firstAndSecondLineLayoutParentParentViewToRemove = (ViewGroup) firstLineLayoutParentViewToRemove.getParent();

      linearLayoutOneStep.removeView(firstAndSecondLineLayoutParentParentViewToRemove);
      stepElementsIdList.remove(position);

      setIngredientBackgroundAfterDelete();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_step);
    toolbar.setSubtitle(R.string.add_recipe_title_step_three);
    setSupportActionBar(toolbar);
  }

  public int checkPositionOfLayoutToRemove(int elementId, List<StepElementsId> stepElementsIdsList){
    for (int i = 0; i < stepElementsIdsList.size(); i++){
      if (stepElementsIdsList.get(i).getDeleteImageViewId() == elementId){
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
    for(int i = 0; i < linearLayoutOneStep.getChildCount(); i++){
      View linearLayoutOneStepView = linearLayoutOneStep.getChildAt(i);
      if(i % 2 == 0) {
        this.backgroundColorString = "#ffffff";
      }else if(i % 2 == 1){
        this.backgroundColorString = "#CCFFCC";
      }
      linearLayoutOneStepView.setBackgroundColor(Color.parseColor( backgroundColorString));
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
        this.layoutElementsArray[4] = childId;
      }if (i == 2){
        this.layoutElementsArray[5] = childId;
        ViewGroup insideChildElementView = (ViewGroup) findViewById(childId);
        for(int j = 0; j < insideChildElementView.getChildCount(); j++){
          View insideChildOneElementView = insideChildElementView.getChildAt(j);
          insideChildOneElementView.setId(generateViewId());
          int insideChildId = insideChildOneElementView.getId();
          this.layoutElementsArray[j+6] = insideChildId;
          if(j == 3){
            ViewGroup doubleInsideChildElementView = (ViewGroup) findViewById(insideChildId);
            for(int k = 0; k < doubleInsideChildElementView.getChildCount(); k++){
              View doubleInsideChildOneElementView = doubleInsideChildElementView.getChildAt(k);
              doubleInsideChildOneElementView.setId(generateViewId());
              int doubleInsideChildId = doubleInsideChildOneElementView.getId();
              this.layoutElementsArray[k+9] = doubleInsideChildId;
            }
          }
        }
      }
    }
  }

  public void setDeleteImageViews(List<StepElementsId> stepElementsIds){
    for (int i = 0; i < stepElementsIds.size(); i++){
      deleteImageViewsList.add((ImageView) findViewById(stepElementsIds.get(i).getDeleteImageViewId()));
    }
  }

  public void setDeleteImageViewListener(){
    for(ImageView oneImageView : deleteImageViewsList){
      oneImageView.setOnClickListener(this);
    }
  }

  public int generateViewId(){
    return randomNumber.nextInt((1000000000 - 100 + 1) + 100);
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent(AddStepsActivityView.this, AddIngredientsActivityView.class);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent(AddStepsActivityView.this, DisplayAllRecipeElementsActivityView.class);
    startActivity(intent);
  }

}
