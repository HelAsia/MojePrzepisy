package com.moje.przepisy.mojeprzepisy.recipeDetails.stepsDetails;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsDisplayFragment extends Fragment implements StepDisplayContract.View {
  @BindView(R.id.stepsDisplayRecyclerView) RecyclerView stepsDisplayRecyclerView;
  @BindView(R.id.editAndDeleteRecipeRelativeLayout) RelativeLayout editAndDeleteRecipeRelativeLayout;
  private Context context;

  public StepsDisplayFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_steps_display, container, false);

    ButterKnife.bind(this, view);

    StepDisplayContract.Presenter presenter = new StepDisplayPresenter(this, new RecipeRepository(context));
    presenter.setWholeRecipeElements();

    return view;
  }

  @Override
  public int getRecipeId() {
    return getArguments().getInt("id");
  }

  @Override
  public Boolean getIsLogged() {
    return getArguments().getBoolean("isLogged");
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    StepsDisplayAdapter adapter = new StepsDisplayAdapter(context, stepList);
    stepsDisplayRecyclerView.setAdapter(adapter);
    stepsDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(context));
  }

  @Override
  public void setRelativeLayoutVisible() {
    editAndDeleteRecipeRelativeLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void setRelativeLayoutGone() {
    editAndDeleteRecipeRelativeLayout.setVisibility(View.GONE);
  }
}
