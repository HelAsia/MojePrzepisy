package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe;

import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface RecipeRepositoryInterface {

  interface OnRecipeFinishedListener {
    void onRecipeError();
    void onStarsError();
    void onRecipeAdded(Boolean ifAdded);
    void onIngredientsAdded(Boolean ifAdded);
    void onStepsAdded(Boolean ifAdded);
    void onStarsAdded(Boolean ifAdded);
    void setRecipeId(String message);
  }

  interface OnStarsEditListener{
    void onUpdateStarsOrFavorite(int recipeId, int position);
  }

  interface OnStarsEditInRecipeListener{
    void onUpdateStarsOrFavoriteInRecipe(int recipeId);
  }

  interface OnFavoriteListener{
    void onUpdateFavoriteState(Boolean favoriteState);
  }

  interface OnRecipeDisplayListener{
//    void setMainInfoRecipe(Recipe recipe);
//    void setIngredients(List<Ingredient> ingredientList);
//    void setSteps(List<Step> stepList);
//    void setComment(List<Comment> commentList);
//    void setStars(Stars stars);
//    void onRecipeError();
//    void onIngredientsError();
//    void onStepsError();
//    void onCommentError();
//    void onStarsError();
//    void setCommentId(String message);
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
    void setCommentId(String message);
  }

  void addRecipe(List<Recipe> recipeList, OnRecipeFinishedListener listener);
  void addIngredients(List<Ingredient> ingredientList, OnRecipeFinishedListener listener);
  void addStep(List<Step> stepList, OnRecipeFinishedListener listener);
  void addComment(Comment comment, OnCommentsDetailsDisplayListener listener);
  void addFirstStars(Stars stars, OnRecipeFinishedListener listener);
  void editStarsAndHeart(int recipeId, String columnName, int columnValue, OnStarsEditListener listener, int position);
  void editStarsAndHeartInRecipe(final int recipeId, String columnName, int columnValue, OnStarsEditInRecipeListener listener);
  void getRecipe(int recipeId, final OnMainInfoDetailsDisplayListener listener);
  void getIngredients(int recipeId, final OnIngredientsDetailsDisplayListener listener);
  void getSteps(int recipeId, final OnStepsDetailsDisplayListener listener);
  void getComments(int recipeId, final OnCommentsDetailsDisplayListener listener);
  void getRecipeDetailsStars(int recipeId, final OnMainInfoDetailsDisplayListener listener);
  void getFavorite(int recipeId, final OnFavoriteListener listener);
}
