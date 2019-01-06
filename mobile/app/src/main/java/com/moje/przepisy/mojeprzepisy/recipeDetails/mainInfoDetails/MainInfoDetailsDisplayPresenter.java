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

  MainInfoDetailsDisplayPresenter(MainInfoDetailsDisplayContract.View mainInfoDetailsDisplayView, RecipeRepository recipeRepository){
    this.mainInfoDetailsDisplayView = mainInfoDetailsDisplayView;
    this.recipeRepository = recipeRepository;
  }
  @Override
  public void onUpdateStarsOrFavoriteInRecipe(int recipeId) {
    recipeRepository.getRecipeDetailsStars(recipeId, this);
  }

  @Override
  public void setWholeRecipeElements() {
    recipeRepository.getRecipe(mainInfoDetailsDisplayView.getRecipeId(), this);
    recipeRepository.getRecipeDetailsStars(mainInfoDetailsDisplayView.getRecipeId(), this);
  }

  @Override
  public void setMainInfoRecipe(Recipe recipe) {
    if(mainInfoDetailsDisplayView != null) {
      setRelativeLayout(recipe);
      setRecipeName(recipe);
      setMainPicture(recipe);
      setCategory(recipe);
      setPrepareTime(recipe);
      setCookTime(recipe);
      setBakeTime(recipe);

      mainInfoDetailsDisplayView.setRecipeListeners();
    }
  }

  @Override
  public void setStars(Stars stars) {
    if(mainInfoDetailsDisplayView != null){
      mainInfoDetailsDisplayView.setStarCountTextView(String.valueOf(stars.getStarsCount()));
      mainInfoDetailsDisplayView.setFavoritesCountTextView(String.valueOf(stars.getFavoritesCount()));
      mainInfoDetailsDisplayView.setFavoriteImage(stars.getFavorites());
    }
  }

  private void setRelativeLayout(Recipe recipe){
    int userId = PreferenceManager
            .getDefaultSharedPreferences(mainInfoDetailsDisplayView.getContext()).getInt(Constant.PREF_USER_ID, 0);
    if(userId != 0){
      if(userId == recipe.getUserId()){
        mainInfoDetailsDisplayView.setRelativeLayoutVisible();
      }else {
        mainInfoDetailsDisplayView.setRelativeLayoutGone();
      }
    }
  }

  private void setRecipeName(Recipe recipe){
    if (recipe.getName() != null) {
      mainInfoDetailsDisplayView.setRecipeNameTextView(recipe.getName());
    } else {
      mainInfoDetailsDisplayView.setRecipeNameTextView("Brak nazwy przepisu");
    }
  }

  private void setMainPicture(Recipe recipe){
    if (recipe.getMainPictureNumber() != 0) {
      mainInfoDetailsDisplayView.setRecipeImageView(BASE_URL + "recipe/photo/"
              + recipe.getMainPictureNumber());
    }
  }

  private void setCategory(Recipe recipe){
    if (recipe.getCategory() != null) {
      mainInfoDetailsDisplayView.setRecipeCategoryTextView(recipe.getCategory());
    } else {
      mainInfoDetailsDisplayView.setRecipeCategoryTextView("Brak kategorii przepisu");
    }
  }

  private void setPrepareTime(Recipe recipe){
    if (recipe.getPrepareTime() != null) {
      mainInfoDetailsDisplayView.setPreparedTimeTextView(recipe.getPrepareTime());
    } else {
      mainInfoDetailsDisplayView.setPreparedTimeTextView("Brak czasu przygotowania");
    }
  }

  private void setCookTime(Recipe recipe){
    if (recipe.getCookTime() != null) {
      mainInfoDetailsDisplayView.setCookTimeTextView(recipe.getCookTime());
    } else {
      mainInfoDetailsDisplayView.setCookTimeTextView("Brak czasu gotowania");
    }
  }

  private void setBakeTime(Recipe recipe){
    if (recipe.getBakeTime() != null) {
      mainInfoDetailsDisplayView.setBakeTimeTextView(recipe.getBakeTime());
    } else {
      mainInfoDetailsDisplayView.setBakeTimeTextView("Brak czasu pieczenia");
    }
  }

  @Override
  public void onRecipeError() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(),
            "Błąd podczas pobierania przepisu!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStarsError() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(),
            "Błąd podczas pobierania gwiazdek!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void setDeleteRecipeAction() {
    recipeRepository.deleteRecipe(mainInfoDetailsDisplayView.getRecipeId(), this);
  }

  @Override
  public void sendStarsToServer(int rate) {
    recipeRepository.editStarsAndHeartInRecipe(mainInfoDetailsDisplayView.getRecipeId(),
            "stars", rate, this);
  }

  @Override
  public void sendFavouriteToServer(int favorite) {
    recipeRepository.editStarsAndHeartInRecipe(mainInfoDetailsDisplayView.getRecipeId(),
            "favorite", favorite, this);
  }

  @Override
  public void onSuccess() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(),
            "Przepis został usunięty!", Toast.LENGTH_SHORT).show();
    mainInfoDetailsDisplayView.goToMainCardActivity();
  }

  @Override
  public void onError() {
    Toast.makeText(mainInfoDetailsDisplayView.getContext(),
            "Błąd podczas usuwania przepisu!", Toast.LENGTH_SHORT).show();
  }
}
