package com.moje.przepisy.mojeprzepisy.recipe_details.main_info_details_display;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainInfoDetailsDisplayFragment extends Fragment implements MainInfoDetailsDisplayContract.View,
    OnClickListener {
  private MainInfoDetailsDisplayContract.Presenter presenter;
  private int recipeId = 0;
  private Boolean isLogged;
  Context context;
  View view;

  public MainInfoDetailsDisplayFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_main_info_details_display, container, false);
    setView(view);

    presenter = new MainInfoDetailsDisplayPresenter(this, new RecipeRepository(context));

    if(getRecipeId() == 0){
      setRecipeId();
    }
    getIsLogged();
    presenter.setWholeRecipeElements();
    setRecipeListeners();

    return view;
  }

  @Nullable
  @Override
  public View getView() {
    return view;
  }

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.starImageView){
      presenter.setRatingBarStarsVisibility();
    }else if(view.getId() == R.id.heart_image_view){
      presenter.setFavoriteImageAndGetFavoriteState();
    }
  }

  @Override
  public Context getContext(){
    return context;
  }

  @Override
  public void setRecipeListeners() {
    if(isLogged){
      getStarImageView().setOnClickListener(this);
      getFavoritesImageView().setOnClickListener(this);
    }
  }

  @Override
  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId() {
    this.recipeId = getArguments().getInt("recipeId");
  }

  @Override
  public Boolean getIsLogged() {
    this.isLogged = getArguments().getBoolean("isLogged");
    return isLogged;
  }

  @Override
  public TextView getRecipeNameTextView() {
    return (TextView)getView().findViewById(R.id.recipeNameTextView);
  }

  @Override
  public ImageView getRecipeImageView() {
    return (ImageView)getView().findViewById(R.id.recipeImageView);
  }

  @Override
  public TextView getRecipeCategoryTextView() {
    return (TextView)getView().findViewById(R.id.recipeCategoryTextView);
  }

  @Override
  public TextView getPreparedTimeTextView() {
    return (TextView)getView().findViewById(R.id.preparedTimeTextView);
  }

  @Override
  public TextView getCookTimeTextView() {
    return (TextView)getView().findViewById(R.id.cookTimeTextView);
  }

  @Override
  public TextView getBakeTimeTextView() {
    return (TextView)getView().findViewById(R.id.bakeTimeTextView);
  }

  @Override
  public ImageView getStarImageView() {
    return (ImageView)getView().findViewById(R.id.starImageView);
  }

  @Override
  public TextView getStarCountTextView() {
    return (TextView)getView().findViewById(R.id.text_view_star_count);
  }

  @Override
  public ImageView getFavoritesImageView() {
    return (ImageView)getView().findViewById(R.id.heart_image_view);
  }

  @Override
  public TextView getFavoritesCountTextView() {
    return (TextView)getView().findViewById(R.id.text_view_favorites_count);
  }

  @Override
  public RatingBar getRatingBarStars() {
    return (RatingBar)getView().findViewById(R.id.ratingBarStars);
  }

  @Override
  public RelativeLayout getEditAndDeleteRecipeRelativeLayout() {
    return (RelativeLayout)getView().findViewById(R.id.editAndDeleteRecipeRelativeLayout);
  }

  @Override
  public ImageView getEditRecipeImageView() {
    return (ImageView)getView().findViewById(R.id.editUserRecipeImageView);
  }

  @Override
  public ImageView getDeleteRecipeImageView() {
    return (ImageView)getView().findViewById(R.id.deleteUserRecipeImageView);
  }
}
