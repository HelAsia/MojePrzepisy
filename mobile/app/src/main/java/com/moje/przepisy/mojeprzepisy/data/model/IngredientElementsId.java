package com.moje.przepisy.mojeprzepisy.data.model;

public class IngredientElementsId {
  int layoutId;
  int oneIngredientLayoutId;
  int menuImageViewId;
  int ingredientQuantityEditTextId;
  int ingredientUnitSpinnerId;
  int deleteImageViewId;
  int ingredientNameEditTextId;

  public IngredientElementsId(int layoutId, int oneIngredientLayoutId, int menuImageViewId,
      int ingredientQuantityEditTextId, int ingredientUnitSpinnerId, int deleteImageViewId,
      int ingredientNameEditTextId) {
    this.layoutId = layoutId;
    this.oneIngredientLayoutId = oneIngredientLayoutId;
    this.menuImageViewId = menuImageViewId;
    this.ingredientQuantityEditTextId = ingredientQuantityEditTextId;
    this.ingredientUnitSpinnerId = ingredientUnitSpinnerId;
    this.deleteImageViewId = deleteImageViewId;
    this.ingredientNameEditTextId = ingredientNameEditTextId;
  }

  public int getLayoutId() {
    return layoutId;
  }

  public void setLayoutId(int layoutId) {
    this.layoutId = layoutId;
  }

  public int getOneIngredientLayoutId() {
    return oneIngredientLayoutId;
  }

  public void setOneIngredientLayoutId(int oneIngredientLayoutId) {
    this.oneIngredientLayoutId = oneIngredientLayoutId;
  }

  public int getMenuImageViewId() {
    return menuImageViewId;
  }

  public void setMenuImageViewId(int menuImageViewId) {
    this.menuImageViewId = menuImageViewId;
  }

  public int getIngredientQuantityEditTextId() {
    return ingredientQuantityEditTextId;
  }

  public void setIngredientQuantityEditTextId(int ingredientQuantityEditTextId) {
    this.ingredientQuantityEditTextId = ingredientQuantityEditTextId;
  }

  public int getIngredientUnitSpinnerId() {
    return ingredientUnitSpinnerId;
  }

  public void setIngredientUnitSpinnerId(int ingredientUnitSpinnerId) {
    this.ingredientUnitSpinnerId = ingredientUnitSpinnerId;
  }

  public int getDeleteImageViewId() {
    return deleteImageViewId;
  }

  public void setDeleteImageViewId(int deleteImageViewId) {
    this.deleteImageViewId = deleteImageViewId;
  }

  public int getIngredientNameEditTextId() {
    return ingredientNameEditTextId;
  }

  public void setIngredientNameEditTextId(int ingredientNameEditTextId) {
    this.ingredientNameEditTextId = ingredientNameEditTextId;
  }
}
