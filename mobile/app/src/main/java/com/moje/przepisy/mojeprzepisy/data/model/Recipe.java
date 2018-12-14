package com.moje.przepisy.mojeprzepisy.data.model;

public class Recipe {
  private int id;
  private int userId;
  private String name;
  private String mainPicture;
  private int mainPictureNumber;
  private String category;
  private String prepareTime;
  private String cookTime;
  private String bakeTime;

  public Recipe(int id, String name, String mainPicture,
                String category, String prepareTime,
                String cookTime, String bakeTime) {
    this.id = id;
    this.name = name;
    this.mainPicture = mainPicture;
    this.category = category;
    this.prepareTime = prepareTime;
    this.cookTime = cookTime;
    this.bakeTime = bakeTime;
  }

  public Recipe(String name, String mainPicture, String category, String prepareTime, String cookTime,
                String bakeTime) {
    this.name = name;
    this.mainPicture = mainPicture;
    this.category = category;
    this.prepareTime = prepareTime;
    this.cookTime = cookTime;
    this.bakeTime = bakeTime;
  }

  public Recipe(String name, String category, String prepareTime,
                String cookTime, String bakeTime) {
    this.name = name;
    this.category = category;
    this.prepareTime = prepareTime;
    this.cookTime = cookTime;
    this.bakeTime = bakeTime;
  }

  public Recipe(){

  }

  public Recipe(int id, int userId, String name, int mainPictureNumber,
                String category, String prepareTime, String cookTime,
                String bakeTime) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.mainPictureNumber = mainPictureNumber;
    this.category = category;
    this.prepareTime = prepareTime;
    this.cookTime = cookTime;
    this.bakeTime = bakeTime;
  }

  public Recipe(int id, String name, int mainPictureNumber,
                String category, String prepareTime, String cookTime,
                String bakeTime) {
    this.id = id;
    this.name = name;
    this.mainPictureNumber = mainPictureNumber;
    this.category = category;
    this.prepareTime = prepareTime;
    this.cookTime = cookTime;
    this.bakeTime = bakeTime;
  }

  public Recipe(String name, int mainPictureNumber, String category,
                String prepareTime, String cookTime, String bakeTime) {
    this.name = name;
    this.mainPictureNumber = mainPictureNumber;
    this.category = category;
    this.prepareTime = prepareTime;
    this.cookTime = cookTime;
    this.bakeTime = bakeTime;
  }

  public int getId() {
    return id;
  }

  public String getMainPicture() {
    return mainPicture;
  }

  public String getName() {
    return name;
  }

  public String getPrepareTime() {
    return prepareTime;
  }

  public String getCookTime() {
    return cookTime;
  }

  public String getBakeTime() {
    return bakeTime;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMainPicture(String mainPicture) {
    this.mainPicture = mainPicture;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrepareTime(String prepareTime) {
    this.prepareTime = prepareTime;
  }

  public void setCookTime(String cookTime) {
    this.cookTime = cookTime;
  }

  public void setBakeTime(String bakeTime) {
    this.bakeTime = bakeTime;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getMainPictureNumber() {
    return mainPictureNumber;
  }

  public void setMainPictureNumber(int mainPictureNumber) {
    this.mainPictureNumber = mainPictureNumber;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
