package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddStepPresenter implements AddStepContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddStepContract.View stepsView;
  private List<Step> stepList = new ArrayList<>();
  private String backgroundColorString = "#CCFFCC";
  private Gson gson = new Gson();

  public AddStepPresenter(AddStepContract.View stepsView, RecipeRepository recipeRepository){
    this.stepsView = stepsView;
    this.recipeRepository = recipeRepository;
  }

  public void setBackground(View child){
    if(backgroundColorString.equals("#ffffff")){
      this.backgroundColorString = "#CCFFCC";
    }else if(backgroundColorString.equals("#CCFFCC")){
      this.backgroundColorString = "#ffffff";
    }
    child.setBackgroundColor(Color.parseColor(backgroundColorString));
  }

  @Override
  public List<Step> getStepList(){
    return stepList;
  }

  @Override
  public void setStepList(List<Step> stepList) {
    this.stepList = stepList;
  }

  @Override
  public String convertPojoToJsonString(List<Step> step) {
    Type type = new TypeToken<List<Step>>(){}.getType();
    return gson.toJson(step, type);
  }

  @Override
  public void addPojoListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_STEP, jsonList).apply();
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
  public String getPojoListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_STEP, null);
  }

  @Override
  public List<Step> getStepListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Step>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }
}
