package com.moje.przepisy.mojeprzepisy.data.model;

public class Step {
  private int stepId;
  private int recipeId;
  private String photoId;
  private int stepNumber;
  private String stepDescription;

  public Step(){

  }

  public Step(String photoId, int stepNumber, String stepDescription){
    this.photoId = photoId;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public int getStepId() {
    return stepId;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public String getPhotoId() {
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

  public void setPhotoId(String photoId) {
    this.photoId = photoId;
  }

  public void setStepNumber(int stepNumber) {
    this.stepNumber = stepNumber;
  }

  public void setStepDescription(String stepDescription) {
    this.stepDescription = stepDescription;
  }
}
