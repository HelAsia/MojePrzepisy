package com.moje.przepisy.mojeprzepisy.recipeDetails.mainInfoDetails;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.squareup.picasso.Picasso;

public class MainInfoDetailsDisplayPresenter implements MainInfoDetailsDisplayContract.Presenter,
    RecipeRepository.OnMainInfoDetailsDisplayListener, RecipeRepository.OnStarsEditInRecipeListener,
    RecipeRepository.OnDeleteRecipeDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private MainInfoDetailsDisplayContract.View mainInfoDetailsDisplayView;
  private Boolean favorite = null;


  public MainInfoDetailsDisplayPresenter(MainInfoDetailsDisplayContract.View mainInfoDetailsDisplayView, RecipeRepository recipeRepository){
    this.mainInfoDetailsDisplayView = mainInfoDetailsDisplayView;
    this.recipeRepository = recipeRepository;
  }
  @Override
  public void onUpdateStarsOrFavoriteInRecipe(int recipeId) {
    recipeRepository.getRecipeDetailsStars(recipeId, this);
  }

  @Override
  public void setMainInfoRecipe(Recipe recipe) {
    if(mainInfoDetailsDisplayView != null) {
      int userId = PreferenceManager
          .getDefaultSharedPreferences(mainInfoDetailsDisplayView.getContext()).getInt(Constant.PREF_USER_ID, 0);
      if(userId != 0){
        if(userId == recipe.getUserId()){
          mainInfoDetailsDisplayView.getEditAndDeleteRecipeRelativeLayout().setVisibility(View.VISIBLE);
        }else {
          mainInfoDetailsDisplayView.getEditAndDeleteRecipeRelativeLayout().setVisibility(View.GONE);
        }
      }
      if (mainInfoDetailsDisplayView.getRecipeNameTextView() != null) {
        mainInfoDetailsDisplayView.getRecipeNameTextView().setText(recipe.getName());
      } else {
        mainInfoDetailsDisplayView.getRecipeNameTextView().setText("Brak nazwy przepisu");
      }
      if (mainInfoDetailsDisplayView.getRecipeImageView() != null) {
        Picasso.get().load(BASE_URL + "recipe/photo/" + recipe.getMainPictureNumber()).
            into(mainInfoDetailsDisplayView.getRecipeImageView());
      }
      if (mainInfoDetailsDisplayView.getRecipeCategoryTextView() != null) {
        mainInfoDetailsDisplayView.getRecipeCategoryTextView().setText(recipe.getCategory());
      } else {
        mainInfoDetailsDisplayView.getRecipeCategoryTextView().setText("Brak kategorii przepisu");
      }
      if (mainInfoDetailsDisplayView.getPreparedTimeTextView() != null) {
        mainInfoDetailsDisplayView.getPreparedTimeTextView().setText(recipe.getPrepareTime());
      } else {
        mainInfoDetailsDisplayView.getPreparedTimeTextView().setText("Brak czasu przygotowania");
      }
      if (mainInfoDetailsDisplayView.getCookTimeTextView() != null) {
        mainInfoDetailsDisplayView.getCookTimeTextView().setText(recipe.getCookTime());
      } else {
        mainInfoDetailsDisplayView.getCookTimeTextView().setText("Brak czasu gotowania");
      }
      if (mainInfoDetailsDisplayView.getBakeTimeTextView() != null) {
        mainInfoDetailsDisplayView.getBakeTimeTextView().setText(recipe.getBakeTime());
      } else {
        mainInfoDetailsDisplayView.getBakeTimeTextView().setText("Brak czasu pieczenia");
      }
    }
  }

  @Override
  public void setStars(Stars stars) {
    if(mainInfoDetailsDisplayView != null){
      mainInfoDetailsDisplayView.getStarCountTextView().setText(String.valueOf(stars.getStarsCount()));
      mainInfoDetailsDisplayView.getFavoritesCountTextView().setText(String.valueOf(stars.getFavoritesCount()));
      setFavoriteImage(stars.getFavorites());
    }
  }

  @Override
  public void onRecipeError() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(), "Błąd podczas pobierania przepisu!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStarsError() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(), "Błąd podczas pobierania gwiazdek!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void setWholeRecipeElements() {
    recipeRepository.getRecipe(mainInfoDetailsDisplayView.getRecipeId(), this);
    recipeRepository.getRecipeDetailsStars(mainInfoDetailsDisplayView.getRecipeId(), this);
  }

  @Override
  public void setRatingBarStarsVisibility() {
    mainInfoDetailsDisplayView.getStarImageView().setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(mainInfoDetailsDisplayView.getRatingBarStars().getVisibility() == View.INVISIBLE){
          mainInfoDetailsDisplayView.getRatingBarStars().setVisibility(View.VISIBLE);
          getRatingAndSetVisibility();
        }else {
          mainInfoDetailsDisplayView.getRatingBarStars().setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  @Override
  public void getRatingAndSetVisibility() {
    mainInfoDetailsDisplayView.getRatingBarStars().setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        int rate = (int)v;
        sendStarsToServer(rate);
        mainInfoDetailsDisplayView.getRatingBarStars().setVisibility(View.INVISIBLE);
      }
    });
  }

  @Override
  public void setFavoriteImageAndGetFavoriteState() {
    Drawable heartBorder = mainInfoDetailsDisplayView.getContext().getResources().getDrawable(R.mipmap.ic_favorite_border);
    Drawable heartSolid =  mainInfoDetailsDisplayView.getContext().getResources().getDrawable(R.mipmap.ic_favorite);

    if(!favorite){
      mainInfoDetailsDisplayView.getFavoritesImageView().setImageDrawable(heartBorder);
      sendFavouriteToServer(1);
    }else {
      mainInfoDetailsDisplayView.getFavoritesImageView().setImageDrawable(heartSolid);
      sendFavouriteToServer(0);
    }
  }

  @Override
  public void setFavoriteImage(Boolean favorites) {
    Drawable heartBorder = mainInfoDetailsDisplayView.getContext().getResources().getDrawable(R.mipmap.ic_favorite_border);
    Drawable heartSolid =  mainInfoDetailsDisplayView.getContext().getResources().getDrawable(R.mipmap.ic_favorite);

    if(!favorites){
      mainInfoDetailsDisplayView.getFavoritesImageView().setImageDrawable(heartBorder);
      favorite = favorites;
    }else {
      mainInfoDetailsDisplayView.getFavoritesImageView().setImageDrawable(heartSolid);
      favorite = favorites;
    }
  }

  @Override
  public void setDeleteRecipeAction() {
    recipeRepository.deleteRecipe(mainInfoDetailsDisplayView.getRecipeId(), this);
  }

  @Override
  public void sendStarsToServer(int rate) {
    recipeRepository.editStarsAndHeartInRecipe(mainInfoDetailsDisplayView.getRecipeId(), "stars", rate, this);
  }

  @Override
  public void sendFavouriteToServer(int favorite) {
    recipeRepository.editStarsAndHeartInRecipe(mainInfoDetailsDisplayView.getRecipeId(), "favorite", favorite, this);
  }

  @Override
  public void onSuccess() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(), "Przepis został usunięty!", Toast.LENGTH_SHORT).show();
    mainInfoDetailsDisplayView.goToMainCardActivity();
  }

  @Override
  public void onError() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(), "Błąd podczas usuwania przepisu!", Toast.LENGTH_SHORT).show();
  }
}
