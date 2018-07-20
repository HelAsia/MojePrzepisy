package com.moje.przepisy.mojeprzepisy.data.model;


/**
 * Created by Asia on 2018-05-30.
 */

public class OneRecipeCard {
  public int id;
  public int starsCount;
  public int favoritesCount;
  public String recipeName;
  public String authorName;
  public String photoRecipe;


  public String toString() {
      String out;

      out = String.format(
          "Recipe(%d, %d, %d, '%s', '%s', '%s'",
          this.id,
          this.starsCount,
          this.favoritesCount,
          this.recipeName,
          this.authorName,
          this.photoRecipe
          );
      return out;
  }

  public long getId() {
    return id;
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

  public String getPhotoRecipe() {
    return photoRecipe;
  }

  public void setId(int id) {
    this.id = id;
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

  public void setPhotoRecipe(String photoRecipe) {
    this.photoRecipe = photoRecipe;
  }

}
