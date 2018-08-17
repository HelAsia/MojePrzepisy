package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import java.util.List;

public interface AddIngredientsContract {

  interface View {

    void setToolbar();

    ViewGroup getInsideChildElementView(int childId);

    Context getContext();

  }

  interface Presenter {

    int getPositionOfLayoutToRemove(int elementId, List<IngredientElementsId> ingredientElementsIdList);

    int generateViewId();

    void setChildId(android.view.View child);

    void setBackground(android.view.View child);

    void setIngredientBackgroundAfterDelete(LinearLayout linearLayoutOneIngredient);

    int[] getElementsIdToArray(ViewGroup childElementsView);

    void getInsideChildElementsToArray(ViewGroup insideChildElementView);

    void addLayoutToElementsIdList(android.view.View child, int[] layoutElementsArray);

    List<IngredientElementsId> getIngredientElementsIdList();

    void setIngredientElementsIdList(List<IngredientElementsId> ingredientElementsIdList);

    void setChildWithIdAndBackground(android.view.View child);


    String convertPojoIdToJsonString(List<IngredientElementsId> ingredientElementsIdList);

    String convertPojoToJsonString(List<Ingredient> ingredient);

    void addPojoIdListToPreferences(String jsonList, Context context);

    void addPojoListToPreferences(String jsonList, Context context);

    void deletePojoIdListFromPreferences(Context context);

    void deletePojoListFromPreferences(Context context);

    String getPojoIdListFromPreferences(Context context);

    String getPojoListFromPreferences(Context context);

    List<IngredientElementsId> getIngredientElementsIdListAfterChangeScreen(String jsonList);

    List<Ingredient> getIngredientListAfterChangeScreen(String jsonList);


    void setChildIdAfterChangeScreen(android.view.View child, List<IngredientElementsId> ingredientElementsIdList);
  }

}
