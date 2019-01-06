package com.moje.przepisy.mojeprzepisy.recipeDetails.stepsDetails;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.List;

public class StepDisplayPresenter implements StepDisplayContract.Presenter,
    RecipeRepository.OnStepsDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private StepDisplayContract.View stepDisplayView;

  StepDisplayPresenter(StepDisplayContract.View stepDisplayView,
      RecipeRepository recipeRepository){
    this.stepDisplayView = stepDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setWholeRecipeElements() {
    recipeRepository.getSteps(stepDisplayView.getRecipeId(), this);
  }

  @Override
  public void setSteps(List<Step> stepList) {
    if(stepDisplayView != null){
      int userId = PreferenceManager
          .getDefaultSharedPreferences(stepDisplayView.getContext()).getInt(Constant.PREF_USER_ID, 0);
      if(userId != 0){
        if(userId == stepList.get(0).getUserId()){
          stepDisplayView.setRelativeLayoutVisible();
        }else {
          stepDisplayView.setRelativeLayoutGone();
        }
      }
      stepDisplayView.setStepsRecyclerView(stepList);
    }
  }

  @Override
  public void onStepsError() {
    Toast.makeText(stepDisplayView.getContext(), "Błąd podczas pobierania kroków!",
        Toast.LENGTH_SHORT).show();
  }
}
