package com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients;

import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.moje.przepisy.mojeprzepisy.utils.PojoFileConverter;
import com.moje.przepisy.mojeprzepisy.utils.PojoJsonConverter;

import java.util.ArrayList;
import java.util.List;

public class AddIngredientsPresenter implements AddIngredientsContract.Presenter {
  private AddIngredientsContract.View ingredientsView;
  private List<Ingredient> ingredientList = new ArrayList<>();
  private PojoFileConverter pojoFileConverter;
  private PojoJsonConverter pojoJsonConverter = new PojoJsonConverter();

  public AddIngredientsPresenter(AddIngredientsContract.View ingredientsView){
    this.ingredientsView = ingredientsView;
    pojoFileConverter = new PojoFileConverter(ingredientsView.getContext());
  }

  @Override
  public void previousAction(){
    pojoFileConverter.addPojoListToFile(Constant.INGREDIENTS_FILE_NAME, ingredientList);
    ingredientsView.navigateToPreviousPage();
  }

  @Override
  public void nextAction() {
    pojoFileConverter.addPojoListToFile(Constant.INGREDIENTS_FILE_NAME, ingredientList);
    ingredientsView.navigateToNextPage();
  }

  private void setFirstList(){
    List<Ingredient> ingredientFirstList = getIngredientFirstList();

    if(ingredientFirstList != null){
      ingredientList = ingredientFirstList;
      ingredientsView.setRecyclerView(ingredientList);
    }else {
      setInitialIngredient();
    }
  }

  @Override
  public void setFirstScreen() {
    ingredientsView.setToolbar();
    ingredientsView.setListeners();
    setFirstList();
  }

  @Override
  public void setNextIngredient() {
    setInitialIngredient();
    pojoFileConverter.addPojoListToFile(Constant.INGREDIENTS_FILE_NAME, ingredientList);
  }

  private void setInitialIngredient(){
    Ingredient emptyIngredient = new Ingredient(1, "kg", "np. cukier");
    ingredientList.add(emptyIngredient);
    ingredientsView.setRecyclerView(ingredientList);
  }

  private List<Ingredient> getIngredientFirstList(){
    return pojoJsonConverter.convertJsonToPojo(pojoFileConverter
            .getPojoListFromFile(Constant.INGREDIENTS_FILE_NAME), Constant.INGREDIENTS_FILE_NAME);
  }
}

