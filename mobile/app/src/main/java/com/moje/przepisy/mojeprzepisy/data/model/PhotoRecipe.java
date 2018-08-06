package com.moje.przepisy.mojeprzepisy.data.model;

public class PhotoRecipe {
  public int photoId;
  public String photoURL;
  public String photoImage;

  public PhotoRecipe(){

  }

  public int getPhotoId() {
    return photoId;
  }

  public String getPhotoURL() {
    return photoURL;
  }

  public String getPhotoImage() {
    return photoImage;
  }

  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  public void setPhotoURL(String photoURL) {
    this.photoURL = photoURL;
  }

  public void setPhotoImage(String photoImage) {
    this.photoImage = photoImage;
  }
}
