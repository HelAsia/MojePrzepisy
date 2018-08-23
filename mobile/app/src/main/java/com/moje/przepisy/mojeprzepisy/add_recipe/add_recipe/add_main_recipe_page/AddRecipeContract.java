package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.content.Intent;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import java.util.List;

public interface AddRecipeContract {

  interface View{

    void setToolbar();

    Context getContext();

    void setRecipeNameEditText(String recipeName);

    void setMainPhotoImageView(String bitmapString);

    void setCategoryChooseSpinner(String category);

    void setPreparedTimeEditText(java.sql.Time time);

    void setCookTimeEditText(java.sql.Time time);

    void setBakeTimeEditText(java.sql.Time time);

    void loadImageFromCamera(android.view.View view);

    void loadImageFromGallery(android.view.View view);

    void onActivityResult(int requestCode, int resultCode, Intent data);
  }

  interface Presenter{

    List<Recipe> getRecipeList();

    void setRecipe(List<Recipe> recipeList);

    String convertPojoToJsonString(List<Recipe> recipeList);

    void addPojoToPreferences(String json, Context context);

    String getPojoListFromPreferences(Context context);

    List<Recipe> getRecipeAfterChangeScreen(String jsonList);

    void setRecipeValueOnScreen();
  }

}
