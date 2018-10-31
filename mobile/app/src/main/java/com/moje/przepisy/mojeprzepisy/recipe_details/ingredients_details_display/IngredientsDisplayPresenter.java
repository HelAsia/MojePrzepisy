package com.moje.przepisy.mojeprzepisy.recipe_details.ingredients_details_display;

import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import java.util.List;

public class IngredientsDisplayPresenter implements IngredientsDisplayContract.Presenter,
    RecipeRepository.OnIngredientsDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private IngredientsDisplayContract.View ingredientsDisplayView;

  public IngredientsDisplayPresenter(IngredientsDisplayContract.View ingredientsDisplayView,
      RecipeRepository recipeRepository){
    this.ingredientsDisplayView = ingredientsDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setWholeRecipeElements() {
    recipeRepository.getIngredients(ingredientsDisplayView.getRecipeId(), this);
  }

  @Override
  public void setIngredients(List<Ingredient> ingredientList) {
    if(ingredientsDisplayView != null){
      ingredientsDisplayView.setIngredientsRecyclerView(ingredientList);
    }
  }

  @Override
  public void onIngredientsError() {
    Toast.makeText(ingredientsDisplayView.getContext(), "Błąd podczas pobierania składników!",
        Toast.LENGTH_SHORT).show();
  }
}
