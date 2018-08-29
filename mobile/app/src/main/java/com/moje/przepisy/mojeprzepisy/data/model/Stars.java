package com.moje.przepisy.mojeprzepisy.data.model;

public class Stars {
  int recipeId;
  int userId;
  int starsCount;
  int favoritesCount;

  public Stars(int recipeId, int starsCount, int favoritesCount) {
    this.recipeId = recipeId;
    this.starsCount = starsCount;
    this.favoritesCount = favoritesCount;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getStarsCount() {
    return starsCount;
  }

  public void setStarsCount(int starsCount) {
    this.starsCount = starsCount;
  }

  public int getFavoritesCount() {
    return favoritesCount;
  }

  public void setFavoritesCount(int favoritesCount) {
    this.favoritesCount = favoritesCount;
  }
}
