package com.moje.przepisy.mojeprzepisy.recipe_details.steps_details_display;


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
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsDisplayFragment extends Fragment implements StepDisplayContract.View {
  private StepDisplayContract.Presenter presenter;
  Context context;
  int recipeId;
  Boolean isLogged;
  View view;

  public StepsDisplayFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_steps_display, container, false);
    setView(view);

    presenter = new StepDisplayPresenter(this, new RecipeRepository(context));
    presenter.setWholeRecipeElements();

    return view;
  }

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public int getRecipeId() {
    this.recipeId = getArguments().getInt("recipeId");
    return recipeId;
  }

  @Override
  public Boolean getIsLogged() {
    this.isLogged = getArguments().getBoolean("isLogged");
    return isLogged;
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    StepsDisplayRecipeAdapter adapter = new StepsDisplayRecipeAdapter(context, stepList);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.stepsDisplayRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
  }

  @Override
  public RelativeLayout getEditAndDeleteRecipeRelativeLayout() {
    return (RelativeLayout)getView().findViewById(R.id.editAndDeleteRecipeRelativeLayout);
  }

  @Override
  public ImageView getEditRecipeImageView() {
    return (ImageView)getView().findViewById(R.id.editUserRecipeImageView);
  }
}
