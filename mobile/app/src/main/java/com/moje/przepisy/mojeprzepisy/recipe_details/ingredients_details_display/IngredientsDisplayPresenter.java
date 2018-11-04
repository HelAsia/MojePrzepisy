package com.moje.przepisy.mojeprzepisy.recipe_details.ingredients_details_display;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
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
      int userId = PreferenceManager
          .getDefaultSharedPreferences(ingredientsDisplayView.getContext()).getInt(Constant.PREF_USER_ID, 0);
      if(userId != 0){
        if(userId == ingredientList.get(0).getUserId()){
          ingredientsDisplayView.getEditAndDeleteRecipeRelativeLayout().setVisibility(View.VISIBLE);
        }else {
          ingredientsDisplayView.getEditAndDeleteRecipeRelativeLayout().setVisibility(View.GONE);
        }
      }
      ingredientsDisplayView.setIngredientsRecyclerView(ingredientList);
    }
  }

  @Override
  public void onIngredientsError() {
    Toast.makeText(ingredientsDisplayView.getContext(), "Błąd podczas pobierania składników!",
        Toast.LENGTH_SHORT).show();
  }
}
