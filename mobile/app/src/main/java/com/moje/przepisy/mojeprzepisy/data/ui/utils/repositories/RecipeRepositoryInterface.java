package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface RecipeRepositoryInterface {

  interface OnRecipeFinishedListener {

    void onRecipeError();

    void onRecipeSuccess();

    void onRecipeAdded(Boolean ifAdded);

    void onIngredientsAdded(Boolean ifAdded);

    void onStepsAdded(Boolean ifAdded);
  }

  int getRecipeId(Recipe recipe);

  void addRecipe(List<Recipe> recipeList, OnRecipeFinishedListener listener);

  void addIngredients(List<Ingredient> ingredientList, OnRecipeFinishedListener listener);

  void addStep(List<Step> stepList, OnRecipeFinishedListener listener);
}
