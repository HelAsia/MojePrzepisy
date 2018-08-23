package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface DisplayAllRecipeElementsContract {

  interface View {

    void setToolbar();

    Context getContext();

 //   void setRecipeRecyclerView(List<Recipe> recipeList);

    void setIngredientsRecyclerView(List<Ingredient> ingredientList);

//    void setStepsRecyclerView(List<Step> stepList);
  }

  interface Presenter {

    List<Recipe> getRecipeList();

    void setRecipeList(List<Recipe> recipe);

    List<Ingredient> getIngredientList();

    void setIngredientList(List<Ingredient> ingredient);

    List<Step> getStepList();

    void setStepList(List<Step> step);

    String getPojoListFromPreferences(Context context);

    List<Ingredient> getIngredientListAfterChangeScreen(String jsonList);

  }

}
