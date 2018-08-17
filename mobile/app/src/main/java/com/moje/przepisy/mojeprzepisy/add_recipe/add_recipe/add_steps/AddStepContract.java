package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import java.util.List;

public interface AddStepContract {

  interface View {

    void setToolbar();

    ViewGroup getChildElementView(android.view.View child);

    ViewGroup getInsideChildElementView(int childId);

  }

  interface Presenter {

    int generateViewId();

    int getPositionOfLayoutToRemove(int elementId, List<StepElementsId> stepElementsIdsList);

    void setBackground(android.view.View child);

    void setIngredientBackgroundAfterDelete(LinearLayout linearLayoutOneStep);

    void setChildId(android.view.View child);

    List<StepElementsId> getStepElementsIdList();

    void setStepElementsIdList(List<StepElementsId> stepElementsIdList);

    int[] getElementsIdToArray(ViewGroup childElementsView);

    void getInsideChildElementsToArray(ViewGroup insideChildElementView, int additionalNumber);

    void getInsideAndDoubleChildElementsToArray(ViewGroup insideChildElementView, int additionalNumber);

    int getInsideChildId();

    int getInsideChildNumber();

    void addLayoutToElementsIdList(android.view.View child, int[] layoutElementsArray);

    void setChildWithIdAndBackground(android.view.View child);

    String convertPojoIdToJsonString(List<StepElementsId> stepElementsIdList);

    String convertPojoToJsonString(List<Step> step);

    void addPojoIdListToPreferences(String jsonList, Context context);

    void addPojoListToPreferences(String jsonList, Context context);

    void deletePojoIdListFromPreferences(Context context);

    void deletePojoListFromPreferences(Context context);

    String getPojoIdListFromPreferences(Context context);

    String getPojoListFromPreferences(Context context);

    List<StepElementsId> getStepElementsIdListAfterChangeScreen(String jsonList);

    List<Step> getStepListAfterChangeScreen(String jsonList);

    void setChildIdAfterChangeScreen(android.view.View child, List<StepElementsId> stepElementsIds);
  }
}
