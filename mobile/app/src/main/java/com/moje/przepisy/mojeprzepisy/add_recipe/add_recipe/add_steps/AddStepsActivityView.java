package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.IngredientsAdapter;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements.DisplayAllRecipeElementsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.ArrayList;
import java.util.List;

public class AddStepsActivityView extends AppCompatActivity implements AddStepContract.View,
    View.OnClickListener {
  @BindView(R.id.addStepFab) FloatingActionButton addStepFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  List<Step> stepList = new ArrayList<>();
  private AddStepContract.Presenter presenter;
  Context context;

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


    stepList = presenter.getStepListAfterChangeScreen(presenter.getPojoListFromPreferences(context));

    if(stepList != null){
      presenter.setStepList(stepList);
      setRecyclerView(presenter.getStepList());
    }else {
      Step emptyStep = new Step("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", 1, "Opis kroku");
      presenter.getStepList().add(emptyStep);
      presenter.setStepList(presenter.getStepList());
      setRecyclerView(presenter.getStepList());
    }
  }

  @Override
  public void setRecyclerView(List<Step> stepList) {
    StepsAdapter adapter = new StepsAdapter(this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addStepsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.addStepFab){

      Step emptyStep = new Step("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", 1, "Opis kroku");

      presenter.getStepList().add(emptyStep);
      presenter.setStepList(presenter.getStepList());
      setRecyclerView(presenter.getStepList());

      String pojoToJson = presenter.convertPojoToJsonString(presenter.getStepList());
      presenter.addPojoListToPreferences(pojoToJson, context);

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();

    }else if((view.getId() == R.id.nextActionFab)) {
      navigateToNextPage();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_step);
    toolbar.setSubtitle(R.string.add_recipe_title_step_three);
    setSupportActionBar(toolbar);
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
