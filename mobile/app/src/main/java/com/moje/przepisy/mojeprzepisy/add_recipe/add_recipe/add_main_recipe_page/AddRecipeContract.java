package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;

public interface AddRecipeContract {

  interface View{

    void setToolbar();

    Context getContext();

    void setRecipeNameEditText(String recipeName);

    void setMainPhotoImageView();

    void setCategoryChooseSpinner(int position);

    void setPreparedTimeEditText(java.sql.Time time);

    void setCookTimeEditText(java.sql.Time time);

    void setBakeTimeEditText(java.sql.Time time);
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
