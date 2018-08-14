package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page.AddRecipeActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps.AddStepsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddIngredientsActivityView extends AppCompatActivity implements AddIngredientsContract.View,
    View.OnClickListener {
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  private AddIngredientsContract.Presenter presenter;
  Context context;
  List<ImageView> deleteImageViewsList = new ArrayList<>();
  List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();

  LinearLayout linearLayoutOneIngredient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddIngredientsPresenter(this, new RecipeRepository(context));

    linearLayoutOneIngredient = (LinearLayout) findViewById(R.id.addIngredientsLayout);

    addIngredientFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();
  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.addIngredientFab){
      View child = getLayoutInflater().inflate(R.layout.one_ingredient_layout, null);
      child.setId(presenter.generateViewId());

      presenter.setBackground(child);
      linearLayoutOneIngredient.addView(child);

      ViewGroup childElementsView = getChildElementView(child);
      presenter.getElementsIdToArray(childElementsView);

      ingredientElementsIdList.add(presenter.getLayoutForIngredient(child));
      setDeleteImageViews(ingredientElementsIdList);
      setDeleteImageViewListener();

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();
    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();
    }else {
      View myViewToRemove = findViewById(view.getId());

      int position = presenter.checkPositionOfLayoutToRemove(view.getId(), ingredientElementsIdList);

      ViewGroup firstLineLayoutParentViewToRemove = (ViewGroup) myViewToRemove.getParent();
      ViewGroup firstAndSecondLineLayoutParentParentViewToRemove = (ViewGroup) firstLineLayoutParentViewToRemove.getParent();

      linearLayoutOneIngredient.removeView(firstAndSecondLineLayoutParentParentViewToRemove);
      ingredientElementsIdList.remove(position);

      presenter.setIngredientBackgroundAfterDelete(linearLayoutOneIngredient);
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_ingredient);
    toolbar.setSubtitle(R.string.add_recipe_title_step_two);
    setSupportActionBar(toolbar);
  }

  public void setIngredientElementsIdList(List<IngredientElementsId> ingredientElementsIdList){
    this.ingredientElementsIdList = ingredientElementsIdList;
  }

  public ViewGroup getChildElementView(View child){
    return (ViewGroup) findViewById(child.getId());
  }

  public ViewGroup getInsideChildElementView(int childId){
    return (ViewGroup) findViewById(childId);
  }

  public void setDeleteImageViews(List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      deleteImageViewsList.add((ImageView) findViewById(ingredientElementsIdList.get(i).getDeleteImageViewId()));
    }
  }

  public void setDeleteImageViewListener(){
    for(ImageView oneImageView : deleteImageViewsList){
      oneImageView.setOnClickListener(this);
    }
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent(AddIngredientsActivityView.this, AddRecipeActivityView.class);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent(AddIngredientsActivityView.this, AddStepsActivityView.class);
    startActivity(intent);
  }

}
