package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddIngredientsPresenter implements AddIngredientsContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddIngredientsContract.View ingredientsView;
  private String backgroundColorString = "#CCFFCC";
  private List<Ingredient> ingredientList = new ArrayList<>();
  private Gson gson = new Gson();

  public AddIngredientsPresenter(AddIngredientsContract.View ingredientsView, RecipeRepository recipeRepository){
    this.ingredientsView = ingredientsView;
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
  public List<Ingredient> getIngredientList(){
    return ingredientList;
  }

  @Override
  public void setIngredientList(List<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }

  @Override
  public String convertPojoToJsonString(List<Ingredient> ingredientList) {
    Type type = new TypeToken<List<Ingredient>>(){}.getType();
    return gson.toJson(ingredientList, type);
  }

  @Override
  public void addPojoListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_INGREDIENT, jsonList).apply();
    editor.commit();
  }

  @Override
  public String getPojoListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_INGREDIENT, null);
  }

  @Override
  public List<Ingredient> getIngredientListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Ingredient>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void setFirstScreen() {
    List<Ingredient> ingredientFirstList = getIngredientListAfterChangeScreen(getPojoListFromPreferences(ingredientsView.getContext()));
    if(ingredientFirstList != null){
      ingredientList = ingredientFirstList;
      setIngredientList(ingredientList);
      ingredientsView.setRecyclerView(ingredientList);
    }else {
      Ingredient emptyIngredient = new Ingredient(1, "kg", "np. cukier");
      ingredientList.add(emptyIngredient);
      setIngredientList(ingredientList);
      ingredientsView.setRecyclerView(ingredientList);
    }
  }

  @Override
  public void setNextStep() {
    Ingredient emptyIngredient = new Ingredient(1, "kg", "np. cukier");

    ingredientList.add(emptyIngredient);
    setIngredientList(ingredientList);
    ingredientsView.setRecyclerView(ingredientList);

    String pojoToJson = convertPojoToJsonString(ingredientList);
    addPojoListToPreferences(pojoToJson, ingredientsView.getContext());
  }
}

