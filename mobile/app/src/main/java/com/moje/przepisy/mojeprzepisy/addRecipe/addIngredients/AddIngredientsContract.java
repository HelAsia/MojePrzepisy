package com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public interface AddIngredientsContract {
  interface View {
    void setToolbar();
    Context getContext();
    void setRecyclerView(List<Ingredient> ingredientList);
    void setListeners();
    void navigateToPreviousPage();
    void navigateToNextPage();
  }

  interface Presenter {
    void previousAction();
    void nextAction();
    void setFirstScreen();
    void setNextIngredient();
  }
}
