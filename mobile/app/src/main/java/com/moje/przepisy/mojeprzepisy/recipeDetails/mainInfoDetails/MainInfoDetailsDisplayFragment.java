package com.moje.przepisy.mojeprzepisy.recipeDetails.mainInfoDetails;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainInfoDetailsDisplayFragment extends Fragment
        implements MainInfoDetailsDisplayContract.View {
  @BindView(R.id.recipeNameTextView) TextView recipeNameTextView;
  @BindView(R.id.recipeImageView) ImageView recipeImageView;
  @BindView(R.id.recipeCategoryTextView) TextView recipeCategoryTextView;
  @BindView(R.id.preparedTimeTextView) TextView preparedTimeTextView;
  @BindView(R.id.cookTimeTextView) TextView cookTimeTextView;
  @BindView(R.id.bakeTimeTextView) TextView bakeTimeTextView;
  @BindView(R.id.starImageView) ImageView starImageView;
  @BindView(R.id.text_view_star_count) TextView starCountTextView;
  @BindView(R.id.heart_image_view) ImageView heartImageView;
  @BindView(R.id.text_view_favorites_count) TextView favoritesCountTextView;
  @BindView(R.id.ratingBarStars) RatingBar ratingBarStars;
  @BindView(R.id.editAndDeleteRecipeRelativeLayout) RelativeLayout editAndDeleteRecipeRelativeLayout;
  @BindView(R.id.editUserRecipeImageView) ImageView editUserRecipeImageView;
  @BindView(R.id.deleteUserRecipeImageView) ImageView deleteUserRecipeImageView;
  private MainInfoDetailsDisplayContract.Presenter presenter;
  private Boolean favorite = null;
  private int recipeId = 0;
  private Boolean isLogged;
  private Context context;
  private View view;

  public MainInfoDetailsDisplayFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_main_info_details_display, container, false);
    setView(view);

    ButterKnife.bind(this, view);

    presenter = new MainInfoDetailsDisplayPresenter(this, new RecipeRepository(context));

    if(getRecipeId() == 0){
      setRecipeId();
    }
    getIsLogged();
    presenter.setWholeRecipeElements();

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
  public Context getContext(){
    return context;
  }

  @Override
  public void setRecipeListeners() {
    if(isLogged){
      starImageView.setOnClickListener(view ->
      setRatingBarStarsVisibility());
      heartImageView.setOnClickListener(view ->
      setFavoriteImageAndGetFavoriteState());
      deleteUserRecipeImageView.setOnClickListener(view ->
      presenter.setDeleteRecipeAction());
    }
  }

  private void setFavoriteImageAndGetFavoriteState() {
    Drawable heartBorder = this.getResources().getDrawable(R.drawable.ic_favorite_border);
    Drawable heartSolid =  this.getResources().getDrawable(R.drawable.ic_favorite);

    if(!favorite){
      heartImageView.setImageDrawable(heartBorder);
      presenter.sendFavouriteToServer(1);
    }else {
      heartImageView.setImageDrawable(heartSolid);
      presenter.sendFavouriteToServer(0);
    }
  }

  private void setRatingBarStarsVisibility(){
    if(ratingBarStars.getVisibility() == View.INVISIBLE){
      ratingBarStars.setVisibility(View.VISIBLE);
      setRatingChangeListener();
    }else {
      ratingBarStars.setVisibility(View.INVISIBLE);
    }
  }

  private void setRatingChangeListener() {
    ratingBarStars.setOnRatingBarChangeListener((ratingBar, v, b) -> {
      int rate = (int)v;
      presenter.sendStarsToServer(rate);
      ratingBarStars.setVisibility(View.INVISIBLE);
    });
  }

  @Override
  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId() {
    this.recipeId = getArguments().getInt("id");
  }

  @Override
  public Boolean getIsLogged() {
    this.isLogged = getArguments().getBoolean("isLogged");
    return isLogged;
  }

  @Override
  public void setRecipeNameTextView(String name) {
    recipeNameTextView.setText(name);
  }

  @Override
  public void goToMainCardActivity(){
    Intent intent = new Intent(context, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
  }

  @Override
  public void setRecipeImageView(String path) {
    Picasso.get().load(path).into(recipeImageView);
  }

  @Override
  public void setRecipeCategoryTextView(String category) {
    recipeCategoryTextView.setText(category);
  }

  @Override
  public void setPreparedTimeTextView(String time) {
    preparedTimeTextView.setText(time);
  }

  @Override
  public void setCookTimeTextView(String time) {
    cookTimeTextView.setText(time);
  }

  @Override
  public void setBakeTimeTextView(String time) {
    bakeTimeTextView.setText(time);
  }

  @Override
  public void setStarCountTextView(String count) {
    starCountTextView.setText(count);
  }

  @Override
  public void setFavoritesCountTextView(String count) {
    favoritesCountTextView.setText(count);
  }

  @Override
  public void setFavoriteImage(Boolean favorites) {
    Drawable heartBorder = this.getResources().getDrawable(R.drawable.ic_favorite_border);
    Drawable heartSolid =  this.getResources().getDrawable(R.drawable.ic_favorite);

    if(!favorites){
      heartImageView.setImageDrawable(heartBorder);
      favorite = false;
    }else {
      heartImageView.setImageDrawable(heartSolid);
      favorite = true;
    }
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
