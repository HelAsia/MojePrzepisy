package com.moje.przepisy.mojeprzepisy.recipe_details;

import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import java.util.List;

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter,
    RecipeRepository.OnRecipeDisplayListener{
  private RecipeRepository recipeRepository;
  private RecipeDetailsContract.View recipeDisplayView;
  private BitmapConverter converter = new BitmapConverter();
  private int recipeId;

  public RecipeDetailsPresenter(RecipeDetailsContract.View recipeDisplayView, RecipeRepository recipeRepository){
    this.recipeDisplayView = recipeDisplayView;
    this.recipeRepository = recipeRepository;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  @Override
  public void setMainInfoRecipe(Recipe recipe) {
    if(recipeDisplayView != null){
      recipeDisplayView.getRecipeNameTextView().setText(recipe.getRecipeName());
      recipeDisplayView.getRecipeImageView().
          setImageBitmap(converter.StringToBitMap(recipe.getRecipeMainPicture()));
      recipeDisplayView.getRecipeCategoryTextView().setText(recipe.getRecipeCategory());
      recipeDisplayView.getPreparedTimeTextView().setText(recipe.getRecipePrepareTime().toString());
      recipeDisplayView.getCookTimeTextView().setText(recipe.getRecipeCookTime().toString());
      recipeDisplayView.getBakeTimeTextView().setText(recipe.getRecipeBakeTime().toString());
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
  }
}
