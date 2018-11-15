package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import java.util.List;

public interface AddRecipeContract {
  interface View{
    void setToolbar();
    Context getContext();
    void setRecipeNameEditText(String recipeName);
    void setMainPhotoImageView(String bitmapString);
    void setCategoryChooseSpinner(String category);
    void setPreparedTimeEditText(String time);
    void setCookTimeEditText(String time);
    void setBakeTimeEditText(String time);
    EditText getRecipeNameEditText();
    ImageView getMainPhotoImageView();
    Spinner getCategoryChooseSpinner();
    TextView getPreparedTimeEditText();
    TextView getCookTimeEditText();
    TextView getBakeTimeEditText();
    void loadImageFromCamera();
    void loadImageFromGallery();
    void setListeners();
  }

  interface Presenter{
    void setRecipe(List<Recipe> recipeList);
    String convertPojoToJsonString(List<Recipe> recipeList);
    String getPojoListFromFile(Context context);
    List<Recipe> getRecipeAfterChangeScreen(String jsonList);
    void setRecipeValueOnScreen();
    void setRecipeValueInFile();
    void setFirstScreen();
    Boolean checkIfValueIsEmpty();
  }
}
