package com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import java.util.List;

public interface AddRecipeContract {
  interface View{
    Context getContext();
    void setRecipeNameEditText(String recipeName);
    void setMainPhotoImageView(String bitmapString);
    void setCategoryChooseSpinner(String category);
    void setPreparedTimeEditText(String time);
    void setCookTimeEditText(String time);
    void setBakeTimeEditText(String time);
    void loadImageFromCamera();
    void loadImageFromGallery();
    String getRecipeName();
    String getMainPhoto();
    String getRecipeCategory();
    String getPrepareTime();
    String getCookTime();
    String getBakeTime();
  }

  interface Presenter{
    void setRecipeValueOnScreen();
    void setRecipeValueInFile();
    void setFirstScreen();
  }
}
