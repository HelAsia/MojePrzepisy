package com.moje.przepisy.mojeprzepisy.data.model;


import java.sql.Timestamp;

/**
 * Created by Asia on 2018-05-30.
 */

public class OneRecipeCard {
  private int id;
  private int starsCount;
  private int favoritesCount;
  private String category;
  private String name;
  private String authorName;
  private String mainPicture;
  private int mainPictureNumber;
  private Timestamp date;
  private Boolean favorite;

  public OneRecipeCard(){

  }

  public OneRecipeCard(String info, int option){
    if(option == 1){
      this.name = info;
    }else if(option == 2){
      this.category = info;
    }
  }

  public int getId() {
    return id;
  }

  public int getStarsCount() {
    return starsCount;
  }

  public int getFavoritesCount() {
    return favoritesCount;
  }

  public String getName() {
    return name;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getMainPicture() {
    return mainPicture;
  }

  public int getMainPictureNumber() {
    return mainPictureNumber;
  }

  public Timestamp getDate() {
    return date;
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

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public void setMainPicture(String mainPicture) {
    this.mainPicture = mainPicture;
  }

  public void setMainPictureNumber(int mainPictureNumber) {
    this.mainPictureNumber = mainPictureNumber;
  }

  public Boolean getFavorite() {
    return favorite;
  }

  public void setFavorite(Boolean favorite) {
    this.favorite = favorite;
  }


}
