package com.moje.przepisy.mojeprzepisy.recipeDetails.ingredientsDetails;


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
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsDisplayFragment extends Fragment implements IngredientsDisplayContract.View {
  private IngredientsDisplayContract.Presenter presenter;
  Context context;
  int recipeId;
  Boolean isLogged;
  View view;

  public IngredientsDisplayFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_ingredients_display, container, false);
    setView(view);

    presenter = new IngredientsDisplayPresenter(this, new RecipeRepository(context));
    presenter.setWholeRecipeElements();

    return view;
  }

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    IngredientsDisplayAdapter adapter = new IngredientsDisplayAdapter(context, ingredientList);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ingredientsDisplayRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
  }

  @Override
  public int getRecipeId() {
    this.recipeId = getArguments().getInt("id");
    return recipeId;
  }

  @Override
  public Boolean getIsLogged() {
    this.isLogged = getArguments().getBoolean("isLogged");
    return isLogged;
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
