package com.moje.przepisy.mojeprzepisy.data.model;

public class Ingredient {
  private int ingredientId;
  private int recipeId;
  private int ingredientQuantity;
  private int ingredientUnit;
  private String ingredientName;
  private String ingredientGroup;

  public Ingredient(int ingredientQuantity, int ingredientUnit, String ingredientName){
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

  public int getIngredientUnit() {
    return ingredientUnit;
  }

  public String getIngredientName() {
    return ingredientName;
  }

  public String getIngredientGroup() {
    return ingredientGroup;
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

  public void setIngredientUnit(int ingredientUnit) {
    this.ingredientUnit = ingredientUnit;
  }

  public void setIngredientName(String ingredientName) {
    this.ingredientName = ingredientName;
  }

  public void setIngredientGroup(String ingredientGroup) {
    this.ingredientGroup = ingredientGroup;
  }
}
