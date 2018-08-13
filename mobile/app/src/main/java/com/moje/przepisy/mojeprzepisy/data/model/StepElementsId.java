package com.moje.przepisy.mojeprzepisy.data.model;

public class StepElementsId {
  int layoutId;
  int oneStepLayoutId;
  int descriptionEditTextId;
  int photoImageViewId;

  public StepElementsId(int layoutId, int oneStepLayoutId, int descriptionEditTextId,
      int photoImageViewId) {
    this.layoutId = layoutId;
    this.oneStepLayoutId = oneStepLayoutId;
    this.descriptionEditTextId = descriptionEditTextId;
    this.photoImageViewId = photoImageViewId;
  }

  public int getLayoutId() {
    return layoutId;
  }

  public void setLayoutId(int layoutId) {
    this.layoutId = layoutId;
  }

  public int getOneStepLayoutId() {
    return oneStepLayoutId;
  }

  public void setOneStepLayoutId(int oneStepLayoutId) {
    this.oneStepLayoutId = oneStepLayoutId;
  }

  public int getDescriptionEditTextId() {
    return descriptionEditTextId;
  }

  public void setDescriptionEditTextId(int descriptionEditTextId) {
    this.descriptionEditTextId = descriptionEditTextId;
  }

  public int getPhotoImageViewId() {
    return photoImageViewId;
  }

  public void setPhotoImageViewId(int photoImageViewId) {
    this.photoImageViewId = photoImageViewId;
  }
}
