package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddStepPresenter implements AddStepContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddStepContract.View stepsView;
  private List<Step> stepList = new ArrayList<>();
  private Gson gson = new Gson();
  private int stepNumber = -1;

  AddStepPresenter(AddStepContract.View stepsView, RecipeRepository recipeRepository){
    this.stepsView = stepsView;
    this.recipeRepository = recipeRepository;
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
  public String getPojoListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_STEP, null);
  }

  @Override
  public List<Step> getStepListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Step>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void setFirstScreen() {
    List<Step> stepFirstList = getStepListAfterChangeScreen(getPojoListFromPreferences(stepsView.getContext()));
    if(stepFirstList != null){
/*      for(Step oneStep : stepFirstList){
        if(oneStep.getPhoto() == null){
          oneStep.setPhoto("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg");
        }
      }*/
      stepList = stepFirstList;
      setStepList(stepList);
      stepsView.setRecyclerView(stepList);
    }else {
      Step emptyStep = new Step("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", -1, "Opis kroku");
      stepList.add(emptyStep);
      setStepList(stepList);
      stepsView.setRecyclerView(stepList);
    }
  }

  @Override
  public void setNextStep() {
    this.stepNumber = stepNumber + 1;
    Step emptyStep = new Step(stepNumber, "Opis kroku");

    stepList = getStepListAfterChangeScreen(getPojoListFromPreferences(stepsView.getContext()));
    stepList.add(emptyStep);
    setStepList(stepList);
    stepsView.setRecyclerView(stepList);

    String pojoToJson = convertPojoToJsonString(stepList);
    addPojoListToPreferences(pojoToJson, stepsView.getContext());
  }
}
