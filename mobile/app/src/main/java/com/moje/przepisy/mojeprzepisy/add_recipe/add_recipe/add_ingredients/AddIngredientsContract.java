package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public interface AddIngredientsContract {

  interface View {

    void setToolbar();

    Context getContext();

    void setRecyclerView(List<Ingredient> ingredientList);

    void setListeners();
  }

  interface Presenter {

    void setBackground(android.view.View child);

    List<Ingredient> getIngredientList();

    void setIngredientList(List<Ingredient> ingredient);

    String convertPojoToJsonString(List<Ingredient> ingredient);

    void addPojoListToPreferences(String jsonList, Context context);

    String getPojoListFromPreferences(Context context);

    List<Ingredient> getIngredientListAfterChangeScreen(String jsonList);

    void setFirstScreen();

    void setNextStep();


  }
}
