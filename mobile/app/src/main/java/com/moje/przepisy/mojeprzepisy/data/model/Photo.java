package com.moje.przepisy.mojeprzepisy.data.model;

import android.graphics.Bitmap;

public class Photo {
  private int photoId;
  private Bitmap photo;
  private String photoString;

  public Photo(int photoId, Bitmap photo) {
    this.photoId = photoId;
    this.photo = photo;
  }

  public Photo(Bitmap photo) {
    this.photo = photo;
  }

  public Photo(String photoString) {
    this.photoString = photoString;
  }

  public Photo(int photoId) {
    this.photoId = photoId;
  }

  public int getPhotoId() {
    return photoId;
  }

  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  public Bitmap getPhoto() {
    return photo;
  }

  public void setPhoto(Bitmap photo) {
    this.photo = photo;
  }

  public String getPhotoString() {
    return photoString;
  }

  public void setPhotoString(String photoString) {
    this.photoString = photoString;
  }
}
