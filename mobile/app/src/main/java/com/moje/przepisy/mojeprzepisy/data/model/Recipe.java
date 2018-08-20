package com.moje.przepisy.mojeprzepisy.data.model;

import java.sql.Timestamp;

public class Recipe {
  public int recipeId;
  public int recipeMainPictureId;
  public String recipeName;
  public String recipeDescription;
  public Timestamp recipePrepareTime;
  public Timestamp recipeCookTime;
  public Timestamp recipeBakeTime;


  public Recipe(){

  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getRecipeMainPictureId() {
    return recipeMainPictureId;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public String getRecipeDescription() {
    return recipeDescription;
  }

  public Timestamp getRecipePrepareTime() {
    return recipePrepareTime;
  }

  public Timestamp getRecipeCookTime() {
    return recipeCookTime;
  }

  public Timestamp getRecipeBakeTime() {
    return recipeBakeTime;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setRecipeMainPictureId(int recipeMainPictureId) {
    this.recipeMainPictureId = recipeMainPictureId;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  public void setRecipeDescription(String recipeDescription) {
    this.recipeDescription = recipeDescription;
  }

  public void setRecipePrepareTime(Timestamp recipePrepareTime) {
    this.recipePrepareTime = recipePrepareTime;
  }

  public void setRecipeCookTime(Timestamp recipeCookTime) {
    this.recipeCookTime = recipeCookTime;
  }

  public void setRecipeBakeTime(Timestamp recipeBakeTime) {
    this.recipeBakeTime = recipeBakeTime;
  }
}
