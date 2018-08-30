package com.moje.przepisy.mojeprzepisy.recipe_details;

import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter{
  private RecipeRepository recipeRepository;
  private RecipeDetailsContract.View recipeDisplayView;

  public RecipeDetailsPresenter(RecipeDetailsContract.View recipeDisplayView, RecipeRepository recipeRepository){
    this.recipeDisplayView = recipeDisplayView;
    this.recipeRepository = recipeRepository;
  }

}
