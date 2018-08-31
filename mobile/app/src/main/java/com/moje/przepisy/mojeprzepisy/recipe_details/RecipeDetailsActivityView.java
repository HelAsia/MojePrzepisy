package com.moje.przepisy.mojeprzepisy.recipe_details;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import java.util.List;
import butterknife.ButterKnife;

public class RecipeDetailsActivityView extends AppCompatActivity implements RecipeDetailsContract.View,
    OnClickListener{
  @BindView(R.id.recipeNameTextView) TextView recipeNameTextView;
  @BindView(R.id.recipeImageView) ImageView recipeImageView;
  @BindView(R.id.recipeCategoryTextView) TextView recipeCategoryTextView;
  @BindView(R.id.preparedTimeTextView) TextView preparedTimeTextView;
  @BindView(R.id.cookTimeTextView) TextView cookTimeTextView;
  @BindView(R.id.bakeTimeTextView) TextView bakeTimeTextView;
  @BindView(R.id.commentEditText) EditText commentEditText;
  @BindView(R.id.addCommentButton) Button addCommentButton;
  @BindView(R.id.starImageView) ImageView starImageView;
  @BindView(R.id.text_view_star_count) TextView starCountTextView;
  @BindView(R.id.heart_image_view) ImageView favoritesImageView;
  @BindView(R.id.text_view_favorites_count) TextView favoritesCountTextView;
  private RecipeDetailsContract.Presenter presenter;
  private int recipeId;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details_view);
    ButterKnife.bind(this);

    presenter = new RecipeDetailsPresenter(this, new RecipeRepository(context));

    setToolbar();
    setListeners();
    presenter.setWholeRecipeElements();
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.commentEditText){

    }else if (view.getId() == R.id.addCommentButton){

    }else if(view.getId() == R.id.starImageView){

    }else if(view.getId() == R.id.heart_image_view){

    }
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_display_recipe);
    setSupportActionBar(toolbar);
  }

  @Override
  public void setListeners(){
    addCommentButton.setOnClickListener(this);
    starImageView.setOnClickListener(this);
    favoritesImageView.setOnClickListener(this);
  }

  @Override
  public int getRecipeId() {
    this.recipeId = getIntent().getExtras().getInt("recipeId");
    return recipeId;
  }
/*
  @Override
  public void setRecipeId(int recipeId) {
    this.recipeId = getIntent().getExtras().getInt("recipeId");
  }

  @Override
  public void getRecipeIdFromIntent(){
    setRecipeId(getIntent().getExtras().getInt("recipeId"));
  }*/

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    IngredientsDisplayRecipeAdapter adapter = new IngredientsDisplayRecipeAdapter(this, ingredientList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ingredientsDisplayRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    StepsDisplayRecipeAdapter adapter = new StepsDisplayRecipeAdapter(this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stepsDisplayRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setCommentsRecyclerView(List<Comment> commentList) {
    CommentDisplayRecipeAdapter adapter = new CommentDisplayRecipeAdapter(this, commentList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.commentsDisplayRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public TextView getRecipeNameTextView() {
    return recipeNameTextView;
  }

  @Override
  public ImageView getRecipeImageView() {
    return recipeImageView;
  }

  @Override
  public TextView getRecipeCategoryTextView() {
    return recipeCategoryTextView;
  }

  @Override
  public TextView getPreparedTimeTextView() {
    return preparedTimeTextView;
  }

  @Override
  public TextView getCookTimeTextView() {
    return cookTimeTextView;
  }

  @Override
  public TextView getBakeTimeTextView() {
    return bakeTimeTextView;
  }

  @Override
  public EditText getCommentEditText() {
    return commentEditText;
  }

  @Override
  public Button getAddCommentButton() {
    return addCommentButton;
  }

  @Override
  public ImageView getStarImageView() {
    return starImageView;
  }

  @Override
  public TextView getStarCountTextView() {
    return starCountTextView;
  }

  @Override
  public ImageView getFavoritesImageView() {
    return favoritesImageView;
  }

  @Override
  public TextView getFavoritesCountTextView() {
    return favoritesCountTextView;
  }
}
