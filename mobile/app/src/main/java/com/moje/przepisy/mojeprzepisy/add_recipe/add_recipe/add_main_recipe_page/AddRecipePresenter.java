package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddRecipePresenter implements AddRecipeContract.Presenter{
  private RecipeRepository recipeRepository;
  private AddRecipeContract.View recipeView;
  private List<Recipe> recipeList = new ArrayList<>();
  private BitmapConverter converter = new BitmapConverter();
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
    recipeView.setMainPhotoImageView(getRecipeList().get(position).getRecipeMainPicture());
    recipeView.setRecipeNameEditText(getRecipeList().get(position).getRecipeName());
    recipeView.setCategoryChooseSpinner(getRecipeList().get(position).getRecipeCategory());
    recipeView.setPreparedTimeEditText(getRecipeList().get(position).getRecipePrepareTime());
    recipeView.setCookTimeEditText(getRecipeList().get(position).getRecipeCookTime());
    recipeView.setBakeTimeEditText(getRecipeList().get(position).getRecipeBakeTime());
  }

  public void setRecipeValueInPreferences(){
    int position = 0;
    getRecipeList().get(position).setRecipeName(recipeView.getRecipeNameEditText().getText().toString());
    BitmapDrawable drawable = (BitmapDrawable) recipeView.getMainPhotoImageView().getDrawable();
    Bitmap bitmap = drawable.getBitmap();
    getRecipeList().get(position).setRecipeMainPicture(converter.BitMapToString(bitmap));
    getRecipeList().get(position).setRecipeCategory((String) recipeView.getCategoryChooseSpinner().getSelectedItem());
    getRecipeList().get(position).setRecipePrepareTime(recipeView.getPreparedTimeEditText().getText().toString());
    getRecipeList().get(position).setRecipeCookTime(recipeView.getCookTimeEditText().getText().toString());
    getRecipeList().get(position).setRecipeBakeTime(recipeView.getBakeTimeEditText().getText().toString());

    String pojoJson = convertPojoToJsonString(getRecipeList());
    addPojoToPreferences(pojoJson, recipeView.getContext());
  }

  public void setFirstScreen(){
    List<Recipe> recipeFirstList = getRecipeAfterChangeScreen(getPojoListFromPreferences(recipeView.getContext()));
    if(recipeFirstList != null){
      recipeList = recipeFirstList;
      setRecipeValueOnScreen();
    }else {
      Recipe recipe = new Recipe("Nazwa przepisu", "https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", "Przekąski", "00:00:00", "00:00:00", "00:00:00");
      recipeList.add(recipe);
      setRecipe(recipeList);
    }
  }

  @Override
  public Boolean checkIfValueIsEmpty() {
    if (recipeView.getRecipeNameEditText().getText().toString().equals("")){
      recipeView.getRecipeNameEditText().setHintTextColor(Color.parseColor("#ff3300"));
      recipeView.getRecipeNameEditText().setHint("Brak nazwy! Uzypełnij nazwę.");
      return true;
    }if(recipeView.getPreparedTimeEditText().getText().toString().equals("HH:MM")){
      recipeView.getPreparedTimeEditText().setText("00:00:00");
    }if(recipeView.getCookTimeEditText().getText().toString().equals("HH:MM")){
      recipeView.getCookTimeEditText().setText("00:00:00");
    }if (recipeView.getBakeTimeEditText().getText().toString().equals("HH:MM")){
      recipeView.getBakeTimeEditText().setText("00:00:00");
    }
    return false;
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
