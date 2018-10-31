package com.moje.przepisy.mojeprzepisy.recipe_details.main_info_details_display;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public interface MainInfoDetailsDisplayContract {
  interface View{
    Context getContext();
    void setRecipeListeners();
    int getRecipeId();
    Boolean getIsLogged();
    TextView getRecipeNameTextView();
    ImageView getRecipeImageView();
    TextView getRecipeCategoryTextView();
    TextView getPreparedTimeTextView();
    TextView getCookTimeTextView();
    TextView getBakeTimeTextView();
    ImageView getStarImageView();
    TextView getStarCountTextView();
    ImageView getFavoritesImageView();
    TextView getFavoritesCountTextView();
    RatingBar getRatingBarStars();
  }

  interface Presenter{
    void setWholeRecipeElements();
    void setRatingBarStarsVisibility();
    void getRatingAndSetVisibility();
    void setFavoriteImageAndGetFavoriteState();
    void sendStarsToServer(int rate);
    void sendFavouriteToServer(int favorite);
  }
}
