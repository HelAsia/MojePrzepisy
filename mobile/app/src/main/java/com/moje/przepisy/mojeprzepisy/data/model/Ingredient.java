package com.moje.przepisy.mojeprzepisy.data.model;

public class Ingredient {
  private int ingredientId;
  private int recipeId;
  private int ingredientQuantity;
  private String ingredientUnit;
  private String ingredientName;

  public Ingredient(int ingredientQuantity, String ingredientUnit, String ingredientName){
    this.ingredientQuantity = ingredientQuantity;
    this.ingredientUnit = ingredientUnit;
    this.ingredientName = ingredientName;
  }

  public Ingredient(int recipeId, int ingredientQuantity, String ingredientUnit,
      String ingredientName) {
    this.recipeId = recipeId;
    this.ingredientQuantity = ingredientQuantity;
    this.ingredientUnit = ingredientUnit;
    this.ingredientName = ingredientName;
  }

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
