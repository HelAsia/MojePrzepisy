package com.moje.przepisy.mojeprzepisy.data.model;

public class Ingredient {
  private int id;
  private int recipeId;
  private int userId;
  private int quantity;
  private String unit;
  private String name;

  public Ingredient(int quantity, String unit, String name){
    this.quantity = quantity;
    this.unit = unit;
    this.name = name;
  }

  public Ingredient(int recipeId, int quantity, String unit,
                    String name) {
    this.recipeId = recipeId;
    this.quantity = quantity;
    this.unit = unit;
    this.name = name;
  }

  public Ingredient(){

  }

  public Ingredient(int id, int recipeId, int userId, int quantity,
                    String unit, String name) {
    this.id = id;
    this.recipeId = recipeId;
    this.userId = userId;
    this.quantity = quantity;
    this.unit = unit;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
