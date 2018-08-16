package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddStepPresenter implements AddStepContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddStepContract.View stepsView;
  private List<StepElementsId> stepElementsIdList = new ArrayList<>();
  private String backgroundColorString = "#CCFFCC";
  private Random randomNumber = new Random();
  private int[] layoutElementsArray = new int[12];
  private int insideChildNumber;
  private int insideChildId;



  public AddStepPresenter(AddStepContract.View stepsView, RecipeRepository recipeRepository){
    this.stepsView = stepsView;
    this.recipeRepository = recipeRepository;
  }

  public int getPositionOfLayoutToRemove(int elementId, List<StepElementsId> stepElementsIdsList){
    for (int i = 0; i < stepElementsIdsList.size(); i++){
      if (stepElementsIdsList.get(i).getDeleteImageViewId() == elementId){
        return i;
      }
    }
    return -1;
  }

  public int generateViewId(){
    return randomNumber.nextInt((1000000000 - 100 + 1) + 100);
  }

  public void setBackground(View child){
    if(backgroundColorString.equals("#ffffff")){
      this.backgroundColorString = "#CCFFCC";
    }else if(backgroundColorString.equals("#CCFFCC")){
      this.backgroundColorString = "#ffffff";
    }
    child.setBackgroundColor(Color.parseColor(backgroundColorString));
  }

  public void setIngredientBackgroundAfterDelete(LinearLayout linearLayoutOneStep){
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

  public List<StepElementsId> getStepElementsIdList(){
    return stepElementsIdList;
  }

  public void setChildId(View child){
    child.setId(generateViewId());
  }

  public int[] getElementsIdToArray(ViewGroup childElementsView){
    for(int i = 0; i < childElementsView.getChildCount(); i++){
      View childOneElementView = childElementsView.getChildAt(i);
      childOneElementView.setId(generateViewId());
      int childId = childOneElementView.getId();
      if (i == 0){
        this.layoutElementsArray[0] = childId;
        getInsideChildElementsToArray(stepsView.getInsideChildElementView(childId), 1);
      }if (i == 1){
        this.layoutElementsArray[4] = childId;
      }if (i == 2){
        this.layoutElementsArray[5] = childId;

        getInsideAndDoubleChildElementsToArray(stepsView.getInsideChildElementView(childId),6);
      }
    }
    return this.layoutElementsArray;
  }

  public void getInsideChildElementsToArray(ViewGroup insideChildElementView, int additionalNumber){
    for(int insideChildNumber = 0; insideChildNumber < insideChildElementView.getChildCount(); insideChildNumber++){
      View insideChildOneElementView = insideChildElementView.getChildAt(insideChildNumber);
      insideChildOneElementView.setId(generateViewId());
      insideChildId = insideChildOneElementView.getId();
      this.layoutElementsArray[insideChildNumber + additionalNumber] = insideChildId;
    }
  }

  public void getInsideAndDoubleChildElementsToArray(ViewGroup insideChildElementView, int additionalNumber){
    getInsideChildElementsToArray(insideChildElementView, additionalNumber);
    if(getInsideChildNumber() == 3){
      ViewGroup doubleInsideChildElementView = stepsView.getInsideChildElementView(getInsideChildId());
      getInsideChildElementsToArray(doubleInsideChildElementView, 9);
    }
  }

  public int getInsideChildId(){
    return insideChildId;
  }

  public int getInsideChildNumber(){
    return insideChildNumber;
  }

  public void addLayoutToElementsIdList(View child, int[] layoutElementsArray){
    StepElementsId layoutForSteps = new StepElementsId(child.getId(), this.layoutElementsArray[0],
        this.layoutElementsArray[1], this.layoutElementsArray[2], this.layoutElementsArray[3], this.layoutElementsArray[4],
        this.layoutElementsArray[5], this.layoutElementsArray[6], this.layoutElementsArray[7], this.layoutElementsArray[8],
        this.layoutElementsArray[9], this.layoutElementsArray[10], this.layoutElementsArray[11]);
    stepElementsIdList.add(layoutForSteps);
  }

  public void setChildWithIdAndBackgroundAndAddToList(View child){
    setChildId(child);
    setBackground(child);
  }
}
