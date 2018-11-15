package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public interface AddIngredientsContract {
  interface View {
    void setToolbar();
    Context getContext();
    List<Ingredient> setIngredientList(List<Ingredient> ingredientList);
    void setRecyclerView(List<Ingredient> ingredientList);
    void setListeners();
  }

  interface Presenter {
    void setIngredientList(List<Ingredient> ingredient);
    String convertPojoToJsonString(List<Ingredient> ingredient);
    void addPojoListToFile();
    String getPojoListFromFile(Context context);
    List<Ingredient> getIngredientListAfterChangeScreen(String jsonList);
    void setFirstScreen();
    void setNextStep();
  }
}
