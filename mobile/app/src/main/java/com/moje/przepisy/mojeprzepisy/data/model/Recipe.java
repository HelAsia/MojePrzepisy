package com.moje.przepisy.mojeprzepisy.data.model;

public class Recipe {
  private int recipeId;
  private String recipeName;
  private String recipeMainPicture;
  private int recipeMainPictureNumber;
  private String recipeCategory;
  private String recipePrepareTime;
  private String recipeCookTime;
  private String recipeBakeTime;

  public Recipe(int recipeId, String recipeName, String recipeMainPicture,
      String recipeCategory, String recipePrepareTime,
      String recipeCookTime, String recipeBakeTime) {
    this.recipeId = recipeId;
    this.recipeName = recipeName;
    this.recipeMainPicture = recipeMainPicture;
    this.recipeCategory = recipeCategory;
    this.recipePrepareTime = recipePrepareTime;
    this.recipeCookTime = recipeCookTime;
    this.recipeBakeTime = recipeBakeTime;
  }

  public Recipe(String recipeName, String recipeMainPicture, String recipeCategory, String recipePrepareTime, String recipeCookTime,
      String recipeBakeTime) {
    this.recipeName = recipeName;
    this.recipeMainPicture = recipeMainPicture;
    this.recipeCategory = recipeCategory;
    this.recipePrepareTime = recipePrepareTime;
    this.recipeCookTime = recipeCookTime;
    this.recipeBakeTime = recipeBakeTime;
  }

  public Recipe(String recipeName, String recipeCategory, String recipePrepareTime,
      String recipeCookTime, String recipeBakeTime) {
    this.recipeName = recipeName;
    this.recipeCategory = recipeCategory;
    this.recipePrepareTime = recipePrepareTime;
    this.recipeCookTime = recipeCookTime;
    this.recipeBakeTime = recipeBakeTime;
  }

  public Recipe(){

  }

  public Recipe(int recipeId, String recipeName, int recipeMainPictureNumber,
      String recipeCategory, String recipePrepareTime, String recipeCookTime,
      String recipeBakeTime) {
    this.recipeId = recipeId;
    this.recipeName = recipeName;
    this.recipeMainPictureNumber = recipeMainPictureNumber;
    this.recipeCategory = recipeCategory;
    this.recipePrepareTime = recipePrepareTime;
    this.recipeCookTime = recipeCookTime;
    this.recipeBakeTime = recipeBakeTime;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public String getRecipeMainPicture() {
    return recipeMainPicture;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public String getRecipePrepareTime() {
    return recipePrepareTime;
  }

  public String getRecipeCookTime() {
    return recipeCookTime;
  }

  public String getRecipeBakeTime() {
    return recipeBakeTime;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setRecipeMainPicture(String recipeMainPicture) {
    this.recipeMainPicture = recipeMainPicture;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  public void setRecipePrepareTime(String recipePrepareTime) {
    this.recipePrepareTime = recipePrepareTime;
  }

  public void setRecipeCookTime(String recipeCookTime) {
    this.recipeCookTime = recipeCookTime;
  }

  public void setRecipeBakeTime(String recipeBakeTime) {
    this.recipeBakeTime = recipeBakeTime;
  }

  public String getRecipeCategory() {
    return recipeCategory;
  }

  public void setRecipeCategory(String recipeCategory) {
    this.recipeCategory = recipeCategory;
  }

  public int getRecipeMainPictureNumber() {
    return recipeMainPictureNumber;
  }

  public void setRecipeMainPictureNumber(int recipeMainPictureNumber) {
    this.recipeMainPictureNumber = recipeMainPictureNumber;
  }
}
