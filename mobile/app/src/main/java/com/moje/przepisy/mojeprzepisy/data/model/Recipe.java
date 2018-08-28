package com.moje.przepisy.mojeprzepisy.data.model;

import java.sql.Time;

public class Recipe {
  private int recipeId;
  private String recipeName;
  private String recipeMainPicture;
  private String recipeCategory;
  private java.sql.Time recipePrepareTime;
  private java.sql.Time recipeCookTime;
  private java.sql.Time recipeBakeTime;

  public Recipe(int recipeId, String recipeName, String recipeMainPicture,
      String recipeCategory, Time recipePrepareTime,
      Time recipeCookTime, Time recipeBakeTime) {
    this.recipeId = recipeId;
    this.recipeName = recipeName;
    this.recipeMainPicture = recipeMainPicture;
    this.recipeCategory = recipeCategory;
    this.recipePrepareTime = recipePrepareTime;
    this.recipeCookTime = recipeCookTime;
    this.recipeBakeTime = recipeBakeTime;
  }

  public Recipe(String recipeName,String recipeMainPicture, String recipeCategory, Time recipePrepareTime, Time recipeCookTime,
      Time recipeBakeTime) {
    this.recipeName = recipeName;
    this.recipeMainPicture = recipeMainPicture;
    this.recipeCategory = recipeCategory;
    this.recipePrepareTime = recipePrepareTime;
    this.recipeCookTime = recipeCookTime;
    this.recipeBakeTime = recipeBakeTime;
  }

  public Recipe(){

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

  public java.sql.Time getRecipePrepareTime() {
    return recipePrepareTime;
  }

  public java.sql.Time getRecipeCookTime() {
    return recipeCookTime;
  }

  public java.sql.Time getRecipeBakeTime() {
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

  public void setRecipePrepareTime(java.sql.Time recipePrepareTime) {
    this.recipePrepareTime = recipePrepareTime;
  }

  public void setRecipeCookTime(java.sql.Time recipeCookTime) {
    this.recipeCookTime = recipeCookTime;
  }

  public void setRecipeBakeTime(java.sql.Time recipeBakeTime) {
    this.recipeBakeTime = recipeBakeTime;
  }

  public String getRecipeCategory() {
    return recipeCategory;
  }

  public void setRecipeCategory(String recipeCategory) {
    this.recipeCategory = recipeCategory;
  }

}
