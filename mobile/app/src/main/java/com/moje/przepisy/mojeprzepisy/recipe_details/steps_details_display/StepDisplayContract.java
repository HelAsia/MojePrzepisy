package com.moje.przepisy.mojeprzepisy.recipe_details.steps_details_display;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface StepDisplayContract {
  interface View{
    Context getContext();
    int getRecipeId();
    Boolean getIsLogged();
    void setStepsRecyclerView(List<Step> stepList);
  }
  interface Presenter{
    void setWholeRecipeElements();
  }
}
