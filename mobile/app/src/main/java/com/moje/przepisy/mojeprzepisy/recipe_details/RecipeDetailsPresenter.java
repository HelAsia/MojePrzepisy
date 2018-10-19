package com.moje.przepisy.mojeprzepisy.recipe_details;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import com.squareup.picasso.Picasso;
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
    if(recipeDisplayView != null) {
      if (recipeDisplayView.getRecipeNameTextView() != null) {
        recipeDisplayView.getRecipeNameTextView().setText(recipe.getRecipeName());
      } else {
        recipeDisplayView.getRecipeNameTextView().setText("Brak nazwy przepisu");
      }
      if (recipeDisplayView.getRecipeImageView() != null) {
        Picasso.get().load(BASE_URL + "recipe/photo/" + recipe.getRecipeMainPictureNumber()).into(recipeDisplayView.getRecipeImageView());
      } else {
        Picasso.get()
            .load("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg")
            .into(recipeDisplayView.getRecipeImageView());
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
