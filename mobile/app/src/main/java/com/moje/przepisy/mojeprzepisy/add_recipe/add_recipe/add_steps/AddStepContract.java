package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface AddStepContract {
  interface View {
    void setToolbar();
    Context getContext();
    List<Step> setStepList(List<Step> stepList);
    void setRecyclerView(List<Step> stepList);
    void loadImageFromCamera();
    void loadImageFromGallery();
    void setListeners();
  }
  interface Presenter {
    String convertPojoToJsonString(List<Step> step);
    void addPojoListToFile();
    String getPojoListFromFile(Context context);
    List<Step> getStepListAfterChangeScreen(String jsonList);
    void setFirstScreen();
    void setNextStep();
  }
}
