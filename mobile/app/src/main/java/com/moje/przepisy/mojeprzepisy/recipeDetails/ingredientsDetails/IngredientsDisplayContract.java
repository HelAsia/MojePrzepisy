package com.moje.przepisy.mojeprzepisy.recipeDetails.ingredientsDetails;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public interface IngredientsDisplayContract {
  interface View{
    Context getContext();
    int getRecipeId();
    Boolean getIsLogged();
    void setEditAndDeleteRecipeRelativeLayout(Boolean isVisible);
    void setIngredientsRecyclerView(List<Ingredient> ingredientList);
  }

  interface Presenter{
    void setWholeRecipeElements();
  }
}
