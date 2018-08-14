package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import java.util.List;

public interface AddIngredientsContract {

  interface View {

    void setToolbar();

    void setIngredientElementsIdList(List<IngredientElementsId> ingredientElementsIdList);

    ViewGroup getInsideChildElementView(int childId);

  }

  interface Presenter {
    int checkPositionOfLayoutToRemove(int elementId, List<IngredientElementsId> ingredientElementsIdList);

    int generateViewId();

    void setBackground(android.view.View child);

    void setIngredientBackgroundAfterDelete(LinearLayout linearLayoutOneIngredient);

    void getElementsIdToArray(ViewGroup childElementsView);

    void getInsideChildElementsToArray(ViewGroup insideChildElementView);

    IngredientElementsId getLayoutForIngredient( android.view.View child);

  }

}
