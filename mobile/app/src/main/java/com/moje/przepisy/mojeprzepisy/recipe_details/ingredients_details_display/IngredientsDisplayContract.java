package com.moje.przepisy.mojeprzepisy.recipe_details.ingredients_details_display;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public interface IngredientsDisplayContract {
  interface View{
    Context getContext();
    int getRecipeId();
    Boolean getIsLogged();
    void setIngredientsRecyclerView(List<Ingredient> ingredientList);
  }

  interface Presenter{
    void setWholeRecipeElements();
  }
}
