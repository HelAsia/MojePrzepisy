package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
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
  private Gson gson = new Gson();

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

  @Override
  public List<StepElementsId> getStepElementsIdList(){
    return stepElementsIdList;
  }

  @Override
  public void setStepElementsIdList(List<StepElementsId> stepElementsIdList) {
    this.stepElementsIdList = stepElementsIdList;
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

  public void setChildWithIdAndBackground(View child){
    setChildId(child);
    setBackground(child);
  }

  @Override
  public String convertPojoIdToJsonString(List<StepElementsId> stepElementsIdList) {
    Type type = new TypeToken<List<StepElementsId>>(){}.getType();
    return gson.toJson(stepElementsIdList, type);
  }

  @Override
  public String convertPojoToJsonString(List<Step> step) {
    Type type = new TypeToken<List<Step>>(){}.getType();
    return gson.toJson(step, type);
  }

  @Override
  public void addPojoIdListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_STEP_ID, jsonList).apply();
    editor.commit();
  }

  @Override
  public void addPojoListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_STEP, jsonList).apply();
    editor.commit();
  }

  @Override
  public void deletePojoIdListFromPreferences(Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.remove(Constant.PREF_STEP_ID);
    editor.apply();
    editor.commit();
  }

  @Override
  public void deletePojoListFromPreferences(Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.remove(Constant.PREF_STEP);
    editor.apply();
    editor.commit();
  }

  @Override
  public String getPojoIdListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_STEP_ID, null);
  }

  @Override
  public String getPojoListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_STEP, null);
  }

  @Override
  public List<StepElementsId> getStepElementsIdListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<StepElementsId>>(){}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public List<Step> getStepListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Step>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void setChildIdAfterChangeScreen(View child, List<StepElementsId> stepElementsIds) {
    for (StepElementsId stepElementsId : stepElementsIds) {
      child.setId(stepElementsId.getLayoutId());
    }
  }
}
