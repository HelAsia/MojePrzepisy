package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class DisplayAllRecipeElementsActivityView extends AppCompatActivity implements DisplayAllRecipeElementsContract.View,
    View.OnClickListener{
  private DisplayAllRecipeElementsContract.Presenter presenter;
  List<Recipe> recipeList = new ArrayList<>();
  List<Ingredient> ingredientList = new ArrayList<>();
  List<Step> stepList = new ArrayList<>();
  Context context;
  @BindView(R.id.saveRecipeImageView)ImageView saveRecipeImageView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_all_recipe_elements_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    saveRecipeImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        // po kliknięciu powinno wysyłać dane do serwera - > dopisać
        SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        ingredientsEditor.remove(Constant.PREF_INGREDIENT);
        ingredientsEditor.apply();
        SharedPreferences.Editor stepsEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        stepsEditor.remove(Constant.PREF_STEP);
        stepsEditor.apply();
        SharedPreferences.Editor recipeEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        recipeEditor.remove(Constant.PREF_RECIPE);
        recipeEditor.apply();
      }
    });

    presenter = new DisplayAllRecipeElementsPresenter(this, new RecipeRepository(context));

    setToolbar();

    recipeList = presenter.getRecipeListAfterChangeScreen(presenter.getRecipeListPojoFromPreferences(context));

    if(recipeList != null){
      presenter.setRecipeList(recipeList);
      setRecipeRecyclerView(presenter.getRecipeList());
    }else {
      Toast.makeText(context, "Nie udało się pobrać głównych informacji o przepisie!", Toast.LENGTH_SHORT).show();
    }

    ingredientList = presenter.getIngredientListAfterChangeScreen(presenter.getIngredientsPojoListFromPreferences(context));

    if(ingredientList != null){
      presenter.setIngredientList(ingredientList);
      setIngredientsRecyclerView(presenter.getIngredientList());
    }else {
      Toast.makeText(context, "Nie udało się pobrać składników!", Toast.LENGTH_SHORT).show();
    }

    stepList = presenter.getStepListAfterChangeScreen(presenter.getStepsPojoListFromPreferences(context));

    if(stepList != null){
      presenter.setStepList(stepList);
      setStepsRecyclerView(presenter.getStepList());
    }else {
      Toast.makeText(context, "Nie udało się pobrać kroków!", Toast.LENGTH_SHORT).show();
    }

  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_whole_recipe);
    toolbar.setSubtitle("Krok 4: Sprawdź wprowdzone informacje");
    setSupportActionBar(toolbar);
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setRecipeRecyclerView(List<Recipe> recipeList) {
    MainRecipeInfoAdapter adapter = new MainRecipeInfoAdapter(this, recipeList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addMainRecipeInfoRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setIngredientsRecyclerView(List<Ingredient> ingredientList) {
    IngredientsDisplayAdapter adapter = new IngredientsDisplayAdapter(this, ingredientList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addIngredientsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void setStepsRecyclerView(List<Step> stepList) {
    StepsDisplayAdapter adapter = new StepsDisplayAdapter (this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addStepsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onClick(View view) {

  }
}
