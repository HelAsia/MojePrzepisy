package com.moje.przepisy.mojeprzepisy;

import android.graphics.Bitmap;

/**
 * Created by Asia on 2018-05-30.
 */

public class OneRecipeCard {
  private long id;
  private String starsCount;
  private String favoritesCount;
  private String recipeName;
  private String authorName;
  private String photoRecipe;


  public long getId() {
    return id;
  }

  public String getStarsCount() {
    return starsCount;
  }

  public String getFavoritesCount() {
    return favoritesCount;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getPhotoRecipe() {
    return photoRecipe;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setStarsCount(String starsCount) {
    this.starsCount = starsCount;
  }

  public void setFavoritesCount(String favoritesCount) {
    this.favoritesCount = favoritesCount;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public void setPhotoRecipe(String photoRecipe) {
    this.photoRecipe = photoRecipe;
  }

}
