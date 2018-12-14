package com.moje.przepisy.mojeprzepisy.recipeDetails.stepsDetails;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface StepDisplayContract {
  interface View{
    Context getContext();
    int getRecipeId();
    Boolean getIsLogged();
    void setStepsRecyclerView(List<Step> stepList);
    RelativeLayout getEditAndDeleteRecipeRelativeLayout();
    ImageView getEditRecipeImageView();
  }
  interface Presenter{
    void setWholeRecipeElements();
  }
}
