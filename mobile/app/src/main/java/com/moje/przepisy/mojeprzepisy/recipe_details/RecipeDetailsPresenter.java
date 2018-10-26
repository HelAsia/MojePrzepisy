package com.moje.przepisy.mojeprzepisy.recipe_details;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter,
    RecipeRepository.OnRecipeDisplayListener, RecipeRepository.OnStarsEditInRecipeListener,
    RecipeRepository.OnFavoriteListener {
  private RecipeRepository recipeRepository;
  private RecipeDetailsContract.View recipeDisplayView;
  private Boolean favorite = null;

  public RecipeDetailsPresenter(RecipeDetailsContract.View recipeDisplayView, RecipeRepository recipeRepository){
    this.recipeDisplayView = recipeDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setMainInfoRecipe(Recipe recipe) {
    if(recipeDisplayView != null) {
      if (recipeDisplayView.getRecipeNameTextView() != null) {
        recipeDisplayView.getRecipeNameTextView().setText(recipe.getRecipeName());
      } else {
        recipeDisplayView.getRecipeNameTextView().setText("Brak nazwy przepisu");
      }
      if (recipeDisplayView.getRecipeImageView() != null) {
        Picasso.get().load(BASE_URL + "recipe/photo/" + recipe.getRecipeMainPictureNumber()).into(recipeDisplayView.getRecipeImageView());
      }
      if (recipeDisplayView.getRecipeCategoryTextView() != null) {
        recipeDisplayView.getRecipeCategoryTextView().setText(recipe.getRecipeCategory());
      } else {
        recipeDisplayView.getRecipeCategoryTextView().setText("Brak kategorii przepisu");
      }
      if (recipeDisplayView.getPreparedTimeTextView() != null) {
        recipeDisplayView.getPreparedTimeTextView().setText(recipe.getRecipePrepareTime());
      } else {
        recipeDisplayView.getPreparedTimeTextView().setText("Brak czasu przygotowania");
      }
      if (recipeDisplayView.getCookTimeTextView() != null) {
        recipeDisplayView.getCookTimeTextView().setText(recipe.getRecipeCookTime());
      } else {
        recipeDisplayView.getCookTimeTextView().setText("Brak czasu gotowania");
      }
      if (recipeDisplayView.getBakeTimeTextView() != null) {
        recipeDisplayView.getBakeTimeTextView().setText(recipe.getRecipeBakeTime());
      } else {
        recipeDisplayView.getBakeTimeTextView().setText("Brak czasu pieczenia");
      }
    }
  }

  @Override
  public void setIngredients(List<Ingredient> ingredientList) {
    if(recipeDisplayView != null){
      recipeDisplayView.setIngredientsRecyclerView(ingredientList);
    }
  }

  @Override
  public void setSteps(List<Step> stepList) {
    if(recipeDisplayView != null){
      recipeDisplayView.setStepsRecyclerView(stepList);
    }
  }

  @Override
  public void setComment(List<Comment> commentList) {
    if(recipeDisplayView != null){
      recipeDisplayView.setCommentsRecyclerView(commentList);
    }
  }

  @Override
  public void setStars(Stars stars) {
    if(recipeDisplayView != null){
      recipeDisplayView.getStarCountTextView().setText(String.valueOf(stars.getStarsCount()));
      recipeDisplayView.getFavoritesCountTextView().setText(String.valueOf(stars.getFavoritesCount()));
      recipeRepository.getFavorite(recipeDisplayView.getRecipeId(), this);
    }
  }

  @Override
  public void onRecipeError() {
    Toast.makeText(recipeDisplayView.getContext(), "Błąd podczas pobierania przepisu!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onIngredientsError() {
    Toast.makeText(recipeDisplayView.getContext(), "Błąd podczas pobierania składników!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStepsError() {
    Toast.makeText(recipeDisplayView.getContext(), "Błąd podczas pobierania kroków!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onCommentError() {
    Toast.makeText(recipeDisplayView.getContext(), "Błąd podczas pobierania komentarzy!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStarsError() {
    Toast.makeText(recipeDisplayView.getContext(), "Błąd podczas pobierania gwiazdek!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void setWholeRecipeElements() {
    recipeRepository.getRecipe(recipeDisplayView.getRecipeId(), this);
    recipeRepository.getIngredients(recipeDisplayView.getRecipeId(), this);
    recipeRepository.getSteps(recipeDisplayView.getRecipeId(), this);
    recipeRepository.getComments(recipeDisplayView.getRecipeId(), this);
    recipeRepository.getRecipeDetailsStars(recipeDisplayView.getRecipeId(), this);
    recipeRepository.getFavorite(recipeDisplayView.getRecipeId(), this);
  }

  @Override
  public void setRatingBarStarsVisibility() {
    recipeDisplayView.getStarImageView().setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(recipeDisplayView.getRatingBarStars().getVisibility() == View.INVISIBLE){
          recipeDisplayView.getRatingBarStars().setVisibility(View.VISIBLE);
          getRatingAndSetVisibility();
        }else {
          recipeDisplayView.getRatingBarStars().setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  @Override
  public void getRatingAndSetVisibility() {
    recipeDisplayView.getRatingBarStars().setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        int rate = (int)v;
        sendStarsToServer(rate);
        recipeDisplayView.getRatingBarStars().setVisibility(View.INVISIBLE);
      }
    });
  }

  @Override
  public void setFavoriteImageAndGetFavoriteState() {
    Drawable heartBorder = recipeDisplayView.getContext().getResources().getDrawable(R.mipmap.ic_favorite_border);
    Drawable heartSolid =  recipeDisplayView.getContext().getResources().getDrawable(R.mipmap.ic_favorite);

    if(!favorite){
      recipeDisplayView.getFavoritesImageView().setImageDrawable(heartSolid);
      sendFavouriteToServer(1);
    }else {
      recipeDisplayView.getFavoritesImageView().setImageDrawable(heartBorder);
      sendFavouriteToServer(0);
    }
  }

  @Override
  public void onUpdateStarsOrFavoriteInRecipe(int recipeId) {
    recipeRepository.getRecipeDetailsStars(recipeId, this);

  }

  public void sendStarsToServer(int rate){
    recipeRepository.editStarsAndHeartInRecipe(recipeDisplayView.getRecipeId(), "stars", rate, this);
  }

  @Override
  public void sendFavouriteToServer(int favorite) {
    recipeRepository.editStarsAndHeartInRecipe(recipeDisplayView.getRecipeId(), "favorite", favorite, this);
  }

  @Override
  public void getFavoriteFromServer() {
    recipeRepository.getFavorite(recipeDisplayView.getRecipeId(), this);
  }

  @Override
  public void onUpdateFavoriteState(Boolean favoriteState) {
    this.favorite = favoriteState;
  }


}
