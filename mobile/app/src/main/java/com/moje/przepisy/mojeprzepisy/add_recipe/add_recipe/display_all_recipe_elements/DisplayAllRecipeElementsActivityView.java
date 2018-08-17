package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsContract;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsPresenter;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class DisplayAllRecipeElementsActivityView extends AppCompatActivity{
  @BindView(R.id.cleanButton) Button cleanButton;
  List<StepElementsId> stepElementsIdList = new ArrayList<>();
  List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_all_recipe_elements_view);

    ButterKnife.bind(this);
    cleanButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        ingredientsEditor.remove(Constant.PREF_INGREDIENT_ID);
        ingredientsEditor.apply();
        SharedPreferences.Editor stepsEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        stepsEditor.remove(Constant.PREF_STEP_ID);
        stepsEditor.apply();
        Toast.makeText(DisplayAllRecipeElementsActivityView.this, "Usunięte", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public List<StepElementsId> getStepElementsIdList() {
    return stepElementsIdList;
  }

  public void setStepElementsIdList(
      List<StepElementsId> stepElementsIdList) {
    this.stepElementsIdList = stepElementsIdList;
  }

  public List<IngredientElementsId> getIngredientElementsIdList() {
    return ingredientElementsIdList;
  }

  public void setIngredientElementsIdList(
      List<IngredientElementsId> ingredientElementsIdList) {
    this.ingredientElementsIdList = ingredientElementsIdList;
  }
}
