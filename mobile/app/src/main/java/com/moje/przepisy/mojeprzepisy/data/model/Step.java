package com.moje.przepisy.mojeprzepisy.data.model;

public class Step {
  private int stepId;
  private int recipeId;
  private String photo;
  private int photoNumber;
  private int stepNumber;
  private String stepDescription;

  public Step(){

  }

  public Step(int stepNumber, String stepDescription) {
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(String photo, int stepNumber, String stepDescription){
    this.photo = photo;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(int recipeId, String photo, int stepNumber, String stepDescription) {
    this.recipeId = recipeId;
    this.photo = photo;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public Step(int recipeId, int photoNumber, int stepNumber, String stepDescription) {
    this.recipeId = recipeId;
    this.photoNumber = photoNumber;
    this.stepNumber = stepNumber;
    this.stepDescription = stepDescription;
  }

  public int getStepId() {
    return stepId;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public String getPhoto() {
    return photo;
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

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public void setStepNumber(int stepNumber) {
    this.stepNumber = stepNumber;
  }

  public void setStepDescription(String stepDescription) {
    this.stepDescription = stepDescription;
  }

  public int getPhotoNumber() {
    return photoNumber;
  }

  public void setPhotoNumber(int photoNumber) {
    this.photoNumber = photoNumber;
  }

  public String toString() {
    return String.format("Step(%d,  %d, '%s')",
        recipeId,
        stepNumber,
        stepDescription
    );
  }
}
