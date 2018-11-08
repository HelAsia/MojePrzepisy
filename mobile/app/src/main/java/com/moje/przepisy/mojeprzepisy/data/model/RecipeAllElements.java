package com.moje.przepisy.mojeprzepisy.data.model;

import java.util.List;

public class RecipeAllElements {
  private List<Recipe> recipeList;
  private List<Ingredient> ingredientList;
  private List<Step> stepList;
  private List<Stars> starsList;

  public RecipeAllElements(
      List<Recipe> recipeList,
      List<Ingredient> ingredientList,
      List<Step> stepList, List<Stars> starsList) {
    this.recipeList = recipeList;
    this.ingredientList = ingredientList;
    this.stepList = stepList;
    this.starsList = starsList;
  }

  public List<Recipe> getRecipeList() {
    return recipeList;
  }

  public void setRecipeList(List<Recipe> recipeList) {
    this.recipeList = recipeList;
  }

  public List<Ingredient> getIngredientList() {
    return ingredientList;
  }

  public void setIngredientList(
      List<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }

  public List<Step> getStepList() {
    return stepList;
  }

  public void setStepList(List<Step> stepList) {
    this.stepList = stepList;
  }

  public List<Stars> getStarsList() {
    return starsList;
  }

  public void setStarsList(List<Stars> starsList) {
    this.starsList = starsList;
  }
}
