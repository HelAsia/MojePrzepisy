package com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo;

import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.moje.przepisy.mojeprzepisy.utils.PojoFileConverter;
import com.moje.przepisy.mojeprzepisy.utils.PojoJsonConverter;
import java.util.ArrayList;
import java.util.List;

public class AddRecipePresenter implements AddRecipeContract.Presenter{
  private AddRecipeContract.View recipeView;
  private List<Recipe> recipeList = new ArrayList<>();
  private PojoFileConverter pojoFileConverter;
  private PojoJsonConverter pojoJsonConverter = new PojoJsonConverter();

  AddRecipePresenter(AddRecipeContract.View recipeView){
    this.recipeView = recipeView;
    pojoFileConverter = new PojoFileConverter(recipeView.getContext());
  }

  @Override
  public void previousAction(){
    setRecipeList();
    pojoFileConverter.addPojoListToFile(Constant.RECIPE_FILE_NAME, recipeList);
    recipeView.navigateToPreviousPage();
  }

  @Override
  public void nextAction() {
    if(!recipeView.checkIfValueIsEmpty()){
      setRecipeList();
      pojoFileConverter.addPojoListToFile(Constant.RECIPE_FILE_NAME, recipeList);
      recipeView.navigateToNextPage();
    }
  }

  private void setFirstList(){
    List<Recipe> recipeFirstList = getRecipeFirstList();

    if(recipeFirstList != null){
      recipeList = recipeFirstList;
      setRecipeValueOnScreen();
    }else {
      setInitialRecipe();
    }
  }

  private void setInitialRecipe(){
    Recipe emptyRecipe = new Recipe("Nazwa przepisu",
            "https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg",
            "Przekąski", "00:00", "00:00", "00:00");
    recipeList.add(emptyRecipe);
    setRecipeList(recipeList);
  }

  private List<Recipe> getRecipeFirstList(){
    return pojoJsonConverter.convertJsonToPojo(pojoFileConverter
            .getPojoListFromFile(Constant.RECIPE_FILE_NAME), Constant.RECIPE_FILE_NAME);
  }

  public void setRecipeValueOnScreen(){
    recipeView.setMainPhotoImageView(recipeList.get(0).getMainPicture());
    recipeView.setRecipeNameEditText(recipeList.get(0).getName());
    recipeView.setCategoryChooseSpinner(recipeList.get(0).getCategory());
    recipeView.setPreparedTimeEditText(recipeList.get(0).getPrepareTime());
    recipeView.setCookTimeEditText(recipeList.get(0).getCookTime());
    recipeView.setBakeTimeEditText(recipeList.get(0).getBakeTime());
  }

  public void setFirstScreen(){
    recipeView.setToolbar();
    recipeView.setFabListeners();
    recipeView.setTimeListeners();
    recipeView.setImageListeners();
    setFirstList();
  }

  private void setRecipeList(List<Recipe> recipeList){
    this.recipeList = recipeList;
  }

  private void setRecipeList() {
    int position = 0;
    recipeList.get(position).setName(recipeView.getRecipeName());
    recipeList.get(position).setMainPicture(recipeView.getMainPhoto());
    recipeList.get(position).setCategory(recipeView.getRecipeCategory());
    recipeList.get(position).setPrepareTime(recipeView.getPrepareTime());
    recipeList.get(position).setCookTime(recipeView.getCookTime());
    recipeList.get(position).setBakeTime(recipeView.getBakeTime());
  }
}
