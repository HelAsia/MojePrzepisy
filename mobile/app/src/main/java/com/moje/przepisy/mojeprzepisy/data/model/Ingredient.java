package com.moje.przepisy.mojeprzepisy.data.model;

public class Ingredient {
  public int ingredientId;
  public int recipeId;
  public int ingredientQuantity;
  public String ingredientUnit;
  public String ingredientName;

  public Ingredient(){

  }

  public int getIngredientId() {
    return ingredientId;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getIngredientQuantity() {
    return ingredientQuantity;
  }

  public String getIngredientUnit() {
    return ingredientUnit;
  }

  public String getIngredientName() {
    return ingredientName;
  }

  public void setIngredientId(int ingredientId) {
    this.ingredientId = ingredientId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setIngredientQuantity(int ingredientQuantity) {
    this.ingredientQuantity = ingredientQuantity;
  }

  public void setIngredientUnit(String ingredientUnit) {
    this.ingredientUnit = ingredientUnit;
  }

  public void setIngredientName(String ingredientName) {
    this.ingredientName = ingredientName;
  }
}
