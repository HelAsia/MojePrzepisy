package com.moje.przepisy.mojeprzepisy.data.model;


import java.sql.Timestamp;

/**
 * Created by Asia on 2018-05-30.
 */

public class OneRecipeCard {
  public int recipeId;
  public int starsCount;
  public int favoritesCount;
  public String recipeName;
  public String authorName;
  public String recipeMainPicture;
  public int recipeMainPictureNumber;
  public Timestamp date;
  public Boolean favorite;

  public OneRecipeCard(){

  }

  public OneRecipeCard(String recipeName){
    this.recipeName = recipeName;
  }

  public OneRecipeCard(int recipeId){
    this.recipeId = recipeId;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getStarsCount() {
    return starsCount;
  }

  public int getFavoritesCount() {
    return favoritesCount;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getRecipeMainPicture() {
    return recipeMainPicture;
  }

  public int getRecipeMainPictureNumber() {
    return recipeMainPictureNumber;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setStarsCount(int starsCount) {
    this.starsCount = starsCount;
  }

  public void setFavoritesCount(int favoritesCount) {
    this.favoritesCount = favoritesCount;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public void setRecipeMainPicture(String recipeMainPicture) {
    this.recipeMainPicture = recipeMainPicture;
  }

  public void setRecipeMainPictureNumber(int recipeMainPictureNumber) {
    this.recipeMainPictureNumber = recipeMainPictureNumber;
  }

  public Boolean getFavorite() {
    return favorite;
  }

  public void setFavorite(Boolean favorite) {
    this.favorite = favorite;
  }


}
