package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddRecipePresenter implements AddRecipeContract.Presenter{
  private RecipeRepository recipeRepository;
  private AddRecipeContract.View recipeView;
  private List<Recipe> recipeList = new ArrayList<>();
  private Gson gson = new Gson();

  public AddRecipePresenter(AddRecipeContract.View recipeView, RecipeRepository recipeRepository){
    this.recipeView = recipeView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public List<Recipe> getRecipeList(){
    return recipeList;
  }

  @Override
  public void setRecipe(List<Recipe> recipeList){
    this.recipeList = recipeList;
  }

  @Override
  public String convertPojoToJsonString(List<Recipe> recipeList) {
    Type type = new TypeToken<List<Recipe>>(){}.getType();
    return gson.toJson(recipeList, type);
  }

  @Override
  public void addPojoToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_RECIPE, jsonList).apply();
    editor.commit();
  }

  public void setRecipeValueOnScreen(){
    int position = 0;
    setRecipe(getRecipeAfterChangeScreen(getPojoListFromPreferences(recipeView.getContext())));
    recipeView.setMainPhotoImageView(getRecipeList().get(position).getRecipeMainPictureId());
    recipeView.setRecipeNameEditText(getRecipeList().get(position).getRecipeName());
    recipeView.setCategoryChooseSpinner(getRecipeList().get(position).getRecipeCategory());
    recipeView.setPreparedTimeEditText(getRecipeList().get(position).getRecipePrepareTime());
    recipeView.setCookTimeEditText(getRecipeList().get(position).getRecipeCookTime());
    recipeView.setBakeTimeEditText(getRecipeList().get(position).getRecipeBakeTime());
  }

  @Override
  public String getPojoListFromPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(Constant.PREF_RECIPE, null);
  }

  @Override
  public List<Recipe> getRecipeAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Recipe>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }
}
