package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import com.moje.przepisy.mojeprzepisy.data.model.StepElementsId;
import java.util.ArrayList;
import java.util.List;

public class DisplayAllRecipeElementsActivityView extends AppCompatActivity {
  List<StepElementsId> stepElementsIdList = new ArrayList<>();
  List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_all_recipe_elements_view);
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
