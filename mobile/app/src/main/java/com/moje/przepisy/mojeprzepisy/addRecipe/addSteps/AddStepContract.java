package com.moje.przepisy.mojeprzepisy.addRecipe.addSteps;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface AddStepContract {
  interface View {
    void setToolbar();
    Context getContext();
    void setRecyclerView(List<Step> stepList);
    void loadImageFromCamera();
    void loadImageFromGallery();
    void setListeners();
    void navigateToPreviousPage();
    void navigateToNextPage();
  }
  interface Presenter {
    void previousAction();
    void nextAction();
    void setFirstScreen();
    void setNextStep();  }
}
