package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import java.util.List;

public interface AddIngredientsContract {

  interface View {

    void setToolbar();

    ViewGroup getInsideChildElementView(int childId);

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

    void setChildWithIdAndBackground(android.view.View child);

  }

}
