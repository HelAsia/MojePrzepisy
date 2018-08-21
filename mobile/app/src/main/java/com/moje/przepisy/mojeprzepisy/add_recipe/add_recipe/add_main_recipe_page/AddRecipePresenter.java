package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class AddRecipePresenter implements AddRecipeContract.Presenter{
  private RecipeRepository recipeRepository;
  private AddRecipeContract.View recipeView;
  private Recipe recipe = new Recipe();
  private Gson gson = new Gson();

  public AddRecipePresenter(AddRecipeContract.View recipeView, RecipeRepository recipeRepository){
    this.recipeView = recipeView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public Recipe getRecipe(){
    return recipe;
  }

  @Override
  public void setRecipe(Recipe recipe){
    this.recipe = recipe;
  }

  @Override
  public String convertPojoToJsonString(Recipe recipe) {
    return gson.toJson(recipe);
  }

  @Override
  public void addPojoToPreferences(String json, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_RECIPE, json).apply();
    editor.commit();
  }

  public void setRecipeValueOnScreen(){
    setRecipe(getRecipeAfterChangeScreen(getPojoFromPreferences(recipeView.getContext())));
    recipeView.setMainPhotoImageView(getRecipe().getRecipeMainPictureId());
    recipeView.setRecipeNameEditText(getRecipe().getRecipeName());
    recipeView.setCategoryChooseSpinner(getRecipe().getRecipeCategory());
    recipeView.setPreparedTimeEditText(getRecipe().getRecipePrepareTime());
    recipeView.setCookTimeEditText(getRecipe().getRecipeCookTime());
    recipeView.setBakeTimeEditText(getRecipe().getRecipeBakeTime());
  }

  @Override
  public String getPojoFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_RECIPE, null);
  }

  @Override
  public Recipe getRecipeAfterChangeScreen(String json) {
    return gson.fromJson(json, Recipe.class);
  }
}
