package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.content.Intent;
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

public class AddIngredientsActivityView extends AppCompatActivity implements AddIngredientsContract.View,
    View.OnClickListener {
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  @BindView(R.id.addIngredientsLayout) LinearLayout linearLayoutOneIngredient;
  private AddIngredientsContract.Presenter presenter;
  Context context;
  List<ImageView> deleteImageViewsList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);

    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddIngredientsPresenter(this, new RecipeRepository(context));

    addIngredientFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();

    if(presenter.getPojoIdListFromPreferences(context) != null){
      List<IngredientElementsId> ingredientElementsIdList = presenter.getIngredientElementsIdListAfterChangeScreen(presenter.getPojoIdListFromPreferences(context));
      presenter.setIngredientElementsIdList(ingredientElementsIdList);
      for(IngredientElementsId ingredientElementsId : ingredientElementsIdList){
        View child = getLayoutInflater().inflate(R.layout.one_ingredient_layout, null);
        child.setId(ingredientElementsId.getLayoutId());
        presenter.setBackground(child);
        linearLayoutOneIngredient.addView(child);

        ViewGroup childElementsView = getChildElementView(child);
        for(int childNumber = 0; childNumber < childElementsView.getChildCount(); childNumber++){
          View childOneElementView = childElementsView.getChildAt(childNumber);
          if (childNumber == 0){
            childOneElementView.setId(ingredientElementsId.getOneIngredientLayoutId());

            ViewGroup insideChildElementView = getInsideChildElementView(childOneElementView.getId());
            for(int insideChildNumber = 0; insideChildNumber < insideChildElementView.getChildCount(); insideChildNumber++){
              View insideChildOneElementView = insideChildElementView.getChildAt(insideChildNumber);
              switch (insideChildNumber){
                case 0:
                  insideChildOneElementView.setId(ingredientElementsId.getMenuImageViewId());
                case 1:
                  insideChildOneElementView.setId(ingredientElementsId.getIngredientQuantityEditTextId());
                case 2:
                  insideChildOneElementView.setId(ingredientElementsId.getIngredientUnitSpinnerId());
                case 3:
                  insideChildOneElementView.setId(ingredientElementsId.getDeleteImageViewId());
              }
            }
          }if (childNumber == 1){
            childElementsView.setId(ingredientElementsId.getIngredientNameEditTextId());
          }
        }
      }
      setDeleteImageViews(ingredientElementsIdList);
      setDeleteImageViewListener();
    }
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.addIngredientFab){
      View child = getLayoutInflater().inflate(R.layout.one_ingredient_layout, null);

      presenter.setChildWithIdAndBackground(child);
      linearLayoutOneIngredient.addView(child);

      int[] elementsIdArray = presenter.getElementsIdToArray(getChildElementView(child));

      presenter.addLayoutToElementsIdList(child, elementsIdArray);

      setDeleteImageViews(presenter.getIngredientElementsIdList());
      setDeleteImageViewListener();

      String pojoIdToJson = presenter.convertPojoIdToJsonString(presenter.getIngredientElementsIdList());
      presenter.addPojoIdListToPreferences(pojoIdToJson, context);

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();

    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();

    }else {
      View myViewToRemove = findViewById(view.getId());

      int position = presenter.getPositionOfLayoutToRemove(view.getId(), presenter.getIngredientElementsIdList());

      ViewGroup firstLineLayoutParentViewToRemove = (ViewGroup) myViewToRemove.getParent();
      ViewGroup firstAndSecondLineLayoutParentParentViewToRemove = (ViewGroup) firstLineLayoutParentViewToRemove.getParent();

      linearLayoutOneIngredient.removeView(firstAndSecondLineLayoutParentParentViewToRemove);
      presenter.getIngredientElementsIdList().remove(position);
      presenter.setIngredientBackgroundAfterDelete(linearLayoutOneIngredient);
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_ingredient);
    toolbar.setSubtitle(R.string.add_recipe_title_step_two);
    setSupportActionBar(toolbar);
  }

  public ViewGroup getChildElementView(View child){
    return (ViewGroup) findViewById(child.getId());
  }

  public ViewGroup getInsideChildElementView(int childId){
    return (ViewGroup) findViewById(childId);
  }

  public void setDeleteImageViews(List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      int foo = ingredientElementsIdList.get(i).getDeleteImageViewId();
      ImageView id = (ImageView) findViewById(foo);
      deleteImageViewsList.add(id);
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
