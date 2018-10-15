package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Photo;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface DisplayAllRecipeElementsContract {

  interface View {

    void setToolbar();

    Context getContext();

    void setRecipeRecyclerView(List<Recipe> recipeList);

    void setIngredientsRecyclerView(List<Ingredient> ingredientList);

    void setStepsRecyclerView(List<Step> stepList);

    TextView getInformationTextView();

    void navigateToMainCardsScreen();

    void navigateToEditRecipeInformation();

    void navigateToEditIngredients();

    void navigateToEditSteps();
  }

  interface Presenter {

    List<Recipe> getRecipeList();

    void setRecipeList(List<Recipe> recipeList);

    List<Photo> getPhotoList();

    void setPhotoList(List<Photo> photoList);

    List<Ingredient> getIngredientList();

    void setIngredientList(List<Ingredient> ingredientList);

    List<Step> getStepList();

    void setStepList(List<Step> stepList);

    String getIngredientsPojoListFromPreferences(Context context);

    List<Ingredient> getIngredientListAfterChangeScreen(String jsonList);

    String getRecipeListPojoFromPreferences(Context context);

    List<Recipe> getRecipeListAfterChangeScreen(String jsonList);

    String getStepsPojoListFromPreferences(Context context);

    List<Step> getStepListAfterChangeScreen(String jsonList);

    void deleteAllSharedPreferences();

    void setRecipeDetailsScreen();

    void setIngredientsDetailScreen();

    void setStepsDetailsScreen();

    void addRecipeToServer();

    void addIngredientsToServer();

    void addStepsToServer();

    void addStarsToServer();

    void addRecipeIdToIngredients();

    void addRecipeIdToSteps();

    void saved();

    Boolean getIfRecipeAdded();

    Boolean getIfIngredientsAdded();

    Boolean getIfStepsAdded();

    void startBackgroundActions(Activity activity);

  }

}
