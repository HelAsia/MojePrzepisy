package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface AddStepContract {

  interface View {

    void setToolbar();

    Context getContext();

    void setRecyclerView(List<Step> stepList);

  }

  interface Presenter {

    List<Step> getStepList();

    void setStepList(List<Step> stepList);

    String convertPojoToJsonString(List<Step> step);

    void addPojoListToPreferences(String jsonList, Context context);

    void deletePojoListFromPreferences(Context context);

    String getPojoListFromPreferences(Context context);

    List<Step> getStepListAfterChangeScreen(String jsonList);
  }
}
