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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsDisplayFragment extends Fragment implements IngredientsDisplayContract.View {
  @BindView(R.id.ingredientsDisplayRecyclerView) RecyclerView recyclerView;
  @BindView(R.id.editAndDeleteRecipeRelativeLayout) RelativeLayout relativeLayout;
  @BindView(R.id.editUserRecipeImageView) ImageView editUserRecipeImageView;
  private Context context;
  private View view;

  public IngredientsDisplayFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_ingredients_display, container, false);
    setView(view);

    ButterKnife.bind(this, view);

    IngredientsDisplayContract.Presenter presenter = new IngredientsDisplayPresenter(this, new RecipeRepository(context));
    presenter.setWholeRecipeElements();

    return view;
  }

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    IngredientsDisplayAdapter adapter = new IngredientsDisplayAdapter(context, ingredientList);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
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
  public void setEditAndDeleteRecipeRelativeLayout(Boolean isVisible) {
    if(isVisible){
      relativeLayout.setVisibility(View.VISIBLE);
    }else {
      relativeLayout.setVisibility(View.GONE);
    }
  }
}
