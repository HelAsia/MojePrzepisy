package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DisplayAllRecipeElementsPresenter implements DisplayAllRecipeElementsContract.Presenter {
  private RecipeRepository recipeRepository;
  private DisplayAllRecipeElementsContract.View recipeElementsView;
  private List<Ingredient> ingredientList = new ArrayList<>();
  private List<Recipe> recipeList = new ArrayList<>();
  private List<Step> stepList = new ArrayList<>();
  private Gson gson = new Gson();

  public DisplayAllRecipeElementsPresenter(DisplayAllRecipeElementsContract.View recipeElementsView, RecipeRepository recipeRepository){
    this.recipeElementsView = recipeElementsView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public List<Recipe> getRecipeList() {
    return recipeList;
  }

  @Override
  public void setRecipeList(List<Recipe> recipeList) {
    this.recipeList = recipeList;
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
  public List<Step> getStepList() {
    return stepList;
  }

  @Override
  public void setStepList(List<Step> stepList) {
    this.stepList = stepList;
  }


  @Override
  public String getIngredientsPojoListFromPreferences(Context context) {
    return PreferenceManager
        .getDefaultSharedPreferences(context).getString(Constant.PREF_INGREDIENT, null);
  }

  @Override
  public List<Ingredient> getIngredientListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Ingredient>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public String getRecipeListPojoFromPreferences(Context context) {
    return PreferenceManager
        .getDefaultSharedPreferences(context).getString(Constant.PREF_RECIPE, null);
  }

  @Override
  public List<Recipe> getRecipeListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Recipe>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public String getStepsPojoListFromPreferences(Context context) {
    return PreferenceManager
        .getDefaultSharedPreferences(context).getString(Constant.PREF_STEP, null);
  }

  @Override
  public List<Step> getStepListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Step>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void deleteAllSharedPreferences() {
    SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(recipeElementsView.getContext()).edit();
    ingredientsEditor.remove(Constant.PREF_INGREDIENT);
    ingredientsEditor.apply();
    SharedPreferences.Editor stepsEditor = PreferenceManager.getDefaultSharedPreferences(recipeElementsView.getContext()).edit();
    stepsEditor.remove(Constant.PREF_STEP);
    stepsEditor.apply();
    SharedPreferences.Editor recipeEditor = PreferenceManager.getDefaultSharedPreferences(recipeElementsView.getContext()).edit();
    recipeEditor.remove(Constant.PREF_RECIPE);
    recipeEditor.apply();
  }

  @Override
  public void setRecipeDetailsScreen() {
    recipeList = getRecipeListAfterChangeScreen(getRecipeListPojoFromPreferences(recipeElementsView.getContext()));

    if(recipeList != null){
      setRecipeList(recipeList);
      recipeElementsView.setRecipeRecyclerView(recipeList);
    }else {
      Toast.makeText(recipeElementsView.getContext(), "Nie udało się pobrać głównych informacji o przepisie!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void setIngredientsDetailScreen() {
    ingredientList = getIngredientListAfterChangeScreen(getIngredientsPojoListFromPreferences(recipeElementsView.getContext()));

    if(ingredientList != null){
      setIngredientList(ingredientList);
      recipeElementsView.setIngredientsRecyclerView(ingredientList);
    }else {
      Toast.makeText(recipeElementsView.getContext(), "Nie udało się pobrać składników!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void setStepsDetailsScreen() {
    stepList = getStepListAfterChangeScreen(getStepsPojoListFromPreferences(recipeElementsView.getContext()));

    if(stepList != null){
      setStepList(stepList);
      recipeElementsView.setStepsRecyclerView(stepList);
    }else {
      Toast.makeText(recipeElementsView.getContext(), "Nie udało się pobrać kroków!", Toast.LENGTH_SHORT).show();
    }
  }
}
