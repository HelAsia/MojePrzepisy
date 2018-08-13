package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;

public class AddIngredientsPresenter implements AddIngredientsContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddIngredientsContract.View ingredientsView;

  public AddIngredientsPresenter(AddIngredientsContract.View ingredientsView, RecipeRepository recipeRepository){
    this.ingredientsView = ingredientsView;
    this.recipeRepository = recipeRepository;
  }
}

