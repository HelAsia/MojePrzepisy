package com.moje.przepisy.mojeprzepisy.recipeDetails.mainInfoDetails;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public interface MainInfoDetailsDisplayContract {
  interface View{
    Context getContext();
    void setRecipeListeners();
    int getRecipeId();
    Boolean getIsLogged();
    void setRecipeNameTextView(String name);
    void setRecipeImageView(String path);
    void setRecipeCategoryTextView(String category);
    void setPreparedTimeTextView(String time);
    void setCookTimeTextView(String time);
    void setBakeTimeTextView(String time);
    void setStarCountTextView(String count);
    void setFavoritesCountTextView(String count);
    void setFavoriteImage(Boolean favorites);
    void setRelativeLayoutVisible();
    void setRelativeLayoutGone();
    void goToMainCardActivity();
  }

  interface Presenter{
    void setWholeRecipeElements();
    void sendStarsToServer(int rate);
    void sendFavouriteToServer(int favorite);
    void setDeleteRecipeAction();
  }
}
