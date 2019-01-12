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

  AddIngredientsPresenter(AddIngredientsContract.View ingredientsView){
    this.ingredientsView = ingredientsView;
    pojoFileConverter = new PojoFileConverter(ingredientsView.getContext());
  }

  @Override
  public void setFirstScreen() {
    ingredientsView.setToolbar();
    ingredientsView.setListeners();
    setFirstList();
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

  private List<Ingredient> getIngredientFirstList(){
    String jsonList = pojoFileConverter.getPojoListFromFile(Constant.INGREDIENTS_FILE_NAME);
    String classType = Constant.INGREDIENTS_FILE_NAME;
    return pojoJsonConverter.convertJsonToPojo(jsonList, classType);
  }

  private void setInitialIngredient(){
    Ingredient emptyIngredient = new Ingredient(1, "kg", "np. cukier");
    ingredientList.add(emptyIngredient);
    ingredientsView.setRecyclerView(ingredientList);
  }

  @Override
  public void previousAction(){
    savePojoList();
    ingredientsView.navigateToPreviousPage();
  }

  @Override
  public void nextAction() {
    savePojoList();
    ingredientsView.navigateToNextPage();
  }

  @Override
  public void setNextIngredient() {
    setInitialIngredient();
    savePojoList();
  }

  private void savePojoList(){
    String fileName = Constant.INGREDIENTS_FILE_NAME;
    pojoFileConverter.addPojoListToFile(fileName, ingredientList);
  }
}

