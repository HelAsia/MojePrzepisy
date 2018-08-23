package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;

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

    String BitMapToString(Bitmap bitmap);

    Bitmap StringToBitMap(String encodedString);
  }

  interface Presenter{

    Recipe getRecipe();

    void setRecipe(Recipe recipe);

    String convertPojoToJsonString(Recipe recipe);

    void addPojoToPreferences(String json, Context context);

    String getPojoFromPreferences(Context context);

    Recipe getRecipeAfterChangeScreen(String json);

    void setRecipeValueOnScreen();
  }

}
