package com.moje.przepisy.mojeprzepisy.data.repositories.recipe;

import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.RecipeAllElements;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface RecipeRepositoryInterface {

  interface OnWholeRecipeElementsFinishedListener {
    void onRecipeError();
    void onWholeRecipeElementsAdded(Boolean ifAdded);
  }

  interface OnAddPhotoFinishedListener {
    void onPhotoError();
    void onSetPhotoNumber(int photoNumber);
  }

  interface OnStarsEditListener{
    void onUpdateStarsOrFavorite(int recipeId, int position);
  }

  interface OnStarsEditInRecipeListener{
    void onUpdateStarsOrFavoriteInRecipe(int recipeId);
  }

  interface OnMainInfoDetailsDisplayListener{
    void setMainInfoRecipe(Recipe recipe);
    void setStars(Stars stars);
    void onRecipeError();
    void onStarsError();
  }

  interface OnIngredientsDetailsDisplayListener{
    void setIngredients(List<Ingredient> ingredientList);
    void onIngredientsError();
  }

  interface OnStepsDetailsDisplayListener{
    void setSteps(List<Step> stepList);
    void onStepsError();
  }

  interface OnCommentsDetailsDisplayListener{
    void setComment(List<Comment> commentList);
    void onCommentError();
    void setCommentAddedState(Boolean state);
  }

  interface OnDeleteCommentsDetailsDisplayListener{
    void onSuccess();
    void onError();
  }

  interface OnDeleteRecipeDetailsDisplayListener{
    void onSuccess();
    void onError();
  }

  interface OnEditCommentsDetailsDisplayListener{
    void onSuccess();
    void onError();
  }

  void addPhoto(String photo, OnAddPhotoFinishedListener listener);
  void addWholeRecipeElements(RecipeAllElements recipeAllElements, OnWholeRecipeElementsFinishedListener listener);
  void addComment(Comment comment, OnCommentsDetailsDisplayListener listener);
  void editStarsAndHeart(int recipeId, String columnName, int columnValue, OnStarsEditListener listener, int position);
  void editStarsAndHeartInRecipe(final int recipeId, String columnName, int columnValue, OnStarsEditInRecipeListener listener);
  void editComment(int id, String columnValue, OnEditCommentsDetailsDisplayListener listener);
  void getRecipe(int recipeId, final OnMainInfoDetailsDisplayListener listener);
  void getIngredients(int recipeId, final OnIngredientsDetailsDisplayListener listener);
  void getSteps(int recipeId, final OnStepsDetailsDisplayListener listener);
  void getComments(int recipeId, final OnCommentsDetailsDisplayListener listener);
  void getRecipeDetailsStars(int recipeId, final OnMainInfoDetailsDisplayListener listener);
  void deleteComment(int commentId, OnDeleteCommentsDetailsDisplayListener listener);
  void deleteRecipe(int commentId, OnDeleteRecipeDetailsDisplayListener listener);
}
