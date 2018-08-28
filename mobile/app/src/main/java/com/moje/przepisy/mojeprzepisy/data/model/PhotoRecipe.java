package com.moje.przepisy.mojeprzepisy.data.model;

public class PhotoRecipe {
  private int photoId;
  private String photoImage;

  public PhotoRecipe(){
  }

  public PhotoRecipe(String photoImage) {
    this.photoImage = photoImage;
  }

  public PhotoRecipe(int photoId, String photoImage) {
    this.photoId = photoId;
    this.photoImage = photoImage;
  }

  public int getPhotoId() {
    return photoId;
  }

  public String getPhotoImage() {
    return photoImage;
  }

  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  public void setPhotoImage(String photoImage) {
    this.photoImage = photoImage;
  }
}
