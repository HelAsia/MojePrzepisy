package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddIngredientsPresenter implements AddIngredientsContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddIngredientsContract.View ingredientsView;
  private String backgroundColorString = "#CCFFCC";
  private Random randomNumber = new Random();
  private int[] layoutElementsArray = new int[6];
  private List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();
  private Gson gson = new Gson();

  public AddIngredientsPresenter(AddIngredientsContract.View ingredientsView, RecipeRepository recipeRepository){
    this.ingredientsView = ingredientsView;
    this.recipeRepository = recipeRepository;
  }

  public int getPositionOfLayoutToRemove(int elementId, List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      if (ingredientElementsIdList.get(i).getDeleteImageViewId() == elementId){
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

  public void setIngredientBackgroundAfterDelete(LinearLayout linearLayoutOneIngredient){
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

  public List<IngredientElementsId> getIngredientElementsIdList(){
    return ingredientElementsIdList;
  }

  public void setChildId(View child){
    child.setId(generateViewId());
  }

  public int[] getElementsIdToArray(ViewGroup childElementsView){
    for(int childNumber = 0; childNumber < childElementsView.getChildCount(); childNumber++){
      View childOneElementView = childElementsView.getChildAt(childNumber);
      childOneElementView.setId(generateViewId());
      int childId = childOneElementView.getId();
      if (childNumber == 0){
        this.layoutElementsArray[0] = childId;
        getInsideChildElementsToArray(ingredientsView.getInsideChildElementView(childId));
      }if (childNumber == 1){
        this.layoutElementsArray[5] = childId;
      }
    }
    return this.layoutElementsArray;
  }
  public void getInsideChildElementsToArray(ViewGroup insideChildElementView){
    for(int insideChildNumber = 0; insideChildNumber < insideChildElementView.getChildCount(); insideChildNumber++){
      View insideChildOneElementView = insideChildElementView.getChildAt(insideChildNumber);
      insideChildOneElementView.setId(generateViewId());
      int insideChildId = insideChildOneElementView.getId();
      this.layoutElementsArray[insideChildNumber + 1] = insideChildId;
    }
  }

  public void addLayoutToElementsIdList(View child, int[] layoutElementsArray){
    IngredientElementsId layoutForIngredient = new IngredientElementsId(child.getId(), this.layoutElementsArray[0],
        this.layoutElementsArray[1], this.layoutElementsArray[2], this.layoutElementsArray[3], this.layoutElementsArray[4], this.layoutElementsArray[5]);
    ingredientElementsIdList.add(layoutForIngredient);
  }

  public void setChildWithIdAndBackground(View child){
    setChildId(child);
    setBackground(child);
  }

  @Override
  public String convertPojoIdToJsonString(List<IngredientElementsId> ingredientElementsIdList) {
    Type type = new TypeToken<List<IngredientElementsId>>(){}.getType();
    return gson.toJson(ingredientElementsIdList, type);
  }

  @Override
  public String convertPojoToJsonString(List<Ingredient> ingredient) {
    Type type = new TypeToken<List<Ingredient>>(){}.getType();
    return gson.toJson(ingredient, type);
  }

  @Override
  public void addPojoIdListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_INGREDIENT_ID, jsonList).apply();
    editor.commit();
  }

  @Override
  public void addPojoListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_INGREDIENT, jsonList).apply();
    editor.commit();
  }

  @Override
  public void deletePojoIdListFromPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.remove(Constant.PREF_INGREDIENT_ID);
    editor.apply();
    editor.commit();
  }

  @Override
  public void deletePojoListFromPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.remove(Constant.PREF_INGREDIENT);
    editor.apply();
    editor.commit();
  }

  @Override
  public String getPojoIdListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_INGREDIENT_ID, null);
  }

  @Override
  public String getPojoListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_INGREDIENT, null);
  }

  @Override
  public List<IngredientElementsId> getIngredientElementsIdListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<IngredientElementsId>>(){}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public List<Ingredient> getIngredientListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Ingredient>>(){}.getType();
    return gson.fromJson(jsonList, type);
  }


}

