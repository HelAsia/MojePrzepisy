package com.moje.przepisy.mojeprzepisy.data.model;

public class Step {
  public int stepId;
  public int recipeId;
  public int photoId;
  public int stepNumber;
  public String stepDescription;

  public Step(){

  }

  public int getStepId() {
    return stepId;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getPhotoId() {
    return photoId;
  }

  public int getStepNumber() {
    return stepNumber;
  }

  public String getStepDescription() {
    return stepDescription;
  }

  public void setStepId(int stepId) {
    this.stepId = stepId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  public void setStepNumber(int stepNumber) {
    this.stepNumber = stepNumber;
  }

  public void setStepDescription(String stepDescription) {
    this.stepDescription = stepDescription;
  }
}
