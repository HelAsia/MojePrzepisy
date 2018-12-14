package com.moje.przepisy.mojeprzepisy.data.model;

import android.graphics.Bitmap;

public class Photo {
  private int id;
  private Bitmap photo;
  private String photoString;

  public Photo(int id, Bitmap photo) {
    this.id = id;
    this.photo = photo;
  }

  public Photo(Bitmap photo) {
    this.photo = photo;
  }

  public Photo(String photoString) {
    this.photoString = photoString;
  }

  public Photo(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
