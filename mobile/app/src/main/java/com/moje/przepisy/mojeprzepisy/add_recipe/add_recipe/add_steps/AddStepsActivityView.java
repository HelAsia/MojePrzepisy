package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

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
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements.DisplayAllRecipeElementsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.ArrayList;
import java.util.List;

public class AddStepsActivityView extends AppCompatActivity implements AddStepContract.View,
    View.OnClickListener {
  @BindView(R.id.addStepFab) FloatingActionButton addStepFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  @BindView(R.id.addsStepsLayout) LinearLayout linearLayoutOneStep;
  private AddStepContract.Presenter presenter;
  Context context;
  List<ImageView> deleteImageViewsList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_steps_view);

    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddStepPresenter(this, new RecipeRepository(context));

    addStepFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.addStepFab){
      View child = getLayoutInflater().inflate(R.layout.one_step_layout, null);

      presenter.setChildWithIdAndBackgroundAndAddToList(child);
      linearLayoutOneStep.addView(child);

      int[] elementsIdArray = presenter.getElementsIdToArray(getChildElementView(child));

      presenter.addLayoutToElementsIdList(child, elementsIdArray);

      setDeleteImageViews(presenter.getStepElementsIdList());
      setDeleteImageViewListener();

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();

    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();

    }else {
      View myViewToRemove = findViewById(view.getId());

      int position = presenter.getPositionOfLayoutToRemove(view.getId(), presenter.getStepElementsIdList());

      ViewGroup firstLineLayoutParentViewToRemove = (ViewGroup) myViewToRemove.getParent();
      ViewGroup firstAndSecondLineLayoutParentParentViewToRemove = (ViewGroup) firstLineLayoutParentViewToRemove.getParent();

      linearLayoutOneStep.removeView(firstAndSecondLineLayoutParentParentViewToRemove);
      presenter.getStepElementsIdList().remove(position);
      presenter.setIngredientBackgroundAfterDelete(linearLayoutOneStep);
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_step);
    toolbar.setSubtitle(R.string.add_recipe_title_step_three);
    setSupportActionBar(toolbar);
  }

  public ViewGroup getChildElementView(View child){
    return (ViewGroup) findViewById(child.getId());
  }

  public ViewGroup getInsideChildElementView(int childId){
    return (ViewGroup) findViewById(childId);
  }

  public void setDeleteImageViews(List<StepElementsId> stepElementsIds){
    for (int i = 0; i < stepElementsIds.size(); i++){
      deleteImageViewsList.add((ImageView) findViewById(stepElementsIds.get(i).getDeleteImageViewId()));
    }
  }

  public void setDeleteImageViewListener() {
    for (ImageView oneImageView : deleteImageViewsList) {
      oneImageView.setOnClickListener(this);
    }
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent(AddStepsActivityView.this, AddIngredientsActivityView.class);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent(AddStepsActivityView.this, DisplayAllRecipeElementsActivityView.class);
    startActivity(intent);
  }

}
