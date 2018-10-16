package com.moje.przepisy.mojeprzepisy.data.model;

import android.graphics.drawable.Drawable;

public class Photo {
  private int photoId;
  private Drawable photo;
  private String photoString;

  public Photo(int photoId, Drawable photo) {
    this.photoId = photoId;
    this.photo = photo;
  }

  public Photo(Drawable photo) {
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

  public Drawable getPhoto() {
    return photo;
  }

  public void setPhoto(Drawable photo) {
    this.photo = photo;
  }

  public String getPhotoString() {
    return photoString;
  }

  public void setPhotoString(String photoString) {
    this.photoString = photoString;
  }
}