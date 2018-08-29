package com.moje.przepisy.mojeprzepisy.data.model;

public class Stars {
  private int recipeId;
  private int userId;
  private int starsCount;
  private int favoritesCount;
  private Boolean favorites;

  public Stars(int recipeId, int starsCount, int favoritesCount) {
    this.recipeId = recipeId;
    this.starsCount = starsCount;
    this.favoritesCount = favoritesCount;
  }

  public Stars(int recipeId, int starsCount) {
    this.recipeId = recipeId;
    this.starsCount = starsCount;
  }

  public Stars(int recipeId, Boolean favorites) {
    this.recipeId = recipeId;
    this.favorites = favorites;
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

  public Boolean getFavorites() {
    return favorites;
  }

  public void setFavorites(Boolean favorites) {
    this.favorites = favorites;
  }
}
