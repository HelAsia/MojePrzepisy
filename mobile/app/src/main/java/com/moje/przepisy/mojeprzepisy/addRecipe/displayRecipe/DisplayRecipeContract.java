package com.moje.przepisy.mojeprzepisy.addRecipe.displayRecipe;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface DisplayRecipeContract {

  interface View {
    void setOnClickListeners();
    void setToolbar();
    Context getContext();
    void setRecipeRecyclerView(List<Recipe> recipeList);
    void setIngredientsRecyclerView(List<Ingredient> ingredientList);
    void setStepsRecyclerView(List<Step> stepList);
    void setInformationTextView(String message);
    void navigateToMainCardsScreen();
    void navigateToEditRecipeInformation();
    void navigateToEditIngredients();
    void navigateToEditSteps();
  }

  interface Presenter {
    void setFirstScreen();
    void setEditRecipeIconAction();
    void setEditIngredientsIconAction();
    void setEditStepsIconAction();
    void startBackgroundActions(Activity activity);
  }
}
