package com.moje.przepisy.mojeprzepisy.addRecipe.displayRecipe;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.RecipeAllElements;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.moje.przepisy.mojeprzepisy.utils.PojoFileConverter;
import com.moje.przepisy.mojeprzepisy.utils.PojoJsonConverter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DisplayRecipePresenter implements DisplayRecipeContract.Presenter,
    RecipeRepository.OnWholeRecipeElementsFinishedListener, RecipeRepository.OnAddPhotoFinishedListener{
  private RecipeRepository recipeRepository;
  private DisplayRecipeContract.View recipeElementsView;
  private PojoJsonConverter pojoJsonConverter = new PojoJsonConverter();
  private PojoFileConverter pojoFileConverter;
  private List<Ingredient> ingredientList = new ArrayList<>();
  private List<Recipe> recipeList = new ArrayList<>();
  private List<Step> stepList = new ArrayList<>();
  private List<Recipe> recipeListWithPhotoNumber = new ArrayList<>();
  private List<Step> stepListWithPhotoNumber = new ArrayList<>();
  private List<Stars> starsList = new ArrayList<>();
  private int photoNumber = -1;
  private Boolean isWholeRecipeAdded = false;

  DisplayRecipePresenter(DisplayRecipeContract.View recipeElementsView, RecipeRepository recipeRepository){
    this.recipeElementsView = recipeElementsView;
    this.recipeRepository = recipeRepository;
    pojoFileConverter = new PojoFileConverter(recipeElementsView.getContext());
  }

  @Override
  public void setFirstScreen() {
    recipeElementsView.setToolbar();
    recipeElementsView.setOnClickListeners();
    setRecipeDetailsScreen();
    setIngredientsDetailScreen();
    setStepsDetailsScreen();
  }

  @Override
  public void setEditRecipeIconAction() {
    recipeElementsView.navigateToEditRecipeInformation();
  }

  @Override
  public void setEditIngredientsIconAction() {
    recipeElementsView.navigateToEditIngredients();
  }

  @Override
  public void setEditStepsIconAction() {
    recipeElementsView.navigateToEditSteps();
  }

  private void deleteAllSharedPreferences() {
    recipeElementsView.getContext().deleteFile("StepsData.txt");
    recipeElementsView.getContext().deleteFile("IngredientsData.txt");
    recipeElementsView.getContext().deleteFile("RecipeData.txt");
  }

  private void setRecipeDetailsScreen() {
    recipeList = pojoJsonConverter.convertJsonToPojo(pojoFileConverter
            .getPojoListFromFile(Constant.RECIPE_FILE_NAME), Constant.RECIPE_FILE_NAME);

    if(recipeList != null){
      recipeElementsView.setRecipeRecyclerView(recipeList);
    }else {
      Toast.makeText(recipeElementsView.getContext(),
              "Nie udało się pobrać głównych informacji o przepisie!", Toast.LENGTH_SHORT).show();
    }
  }

  private void setIngredientsDetailScreen() {
    ingredientList = pojoJsonConverter.convertJsonToPojo(pojoFileConverter
              .getPojoListFromFile(Constant.INGREDIENTS_FILE_NAME), Constant.INGREDIENTS_FILE_NAME);

    if(ingredientList != null){
      recipeElementsView.setIngredientsRecyclerView(ingredientList);
    }else {
      Toast.makeText(recipeElementsView.getContext(),
              "Nie udało się pobrać składników!", Toast.LENGTH_SHORT).show();
    }
  }

  private void setStepsDetailsScreen() {
    stepList = pojoJsonConverter.convertJsonToPojo(pojoFileConverter
            .getPojoListFromFile(Constant.STEPS_FILE_NAME), Constant.STEPS_FILE_NAME);

    if(stepList != null){
      recipeElementsView.setStepsRecyclerView(stepList);
    }else {
      Toast.makeText(recipeElementsView.getContext(),
              "Nie udało się pobrać kroków!", Toast.LENGTH_SHORT).show();
    }
  }

  private void saved() {
    addWholeElementsToServer();
    while (!isWholeRecipeAdded){

    }
  }

  @Override
  public void onRecipeError() {
    if(recipeElementsView != null){
      recipeElementsView
              .setInformationTextView("Wystąpił błąd podczas dodawania przepisu. Spróbuj ponownie.");
    }
  }

  @Override
  public void onWholeRecipeElementsAdded(Boolean ifAdded) {
    this.isWholeRecipeAdded = ifAdded;
  }

  @Override
  public void startBackgroundActions(Activity activity){
    new BackgroundActions(activity).execute();
  }

  private void startBackgroundAddingPhoto(Activity activity){
    new BackgroundAddingPhoto(activity).execute();
  }

  @Override
  public void onPhotoError() {
    Toast.makeText(recipeElementsView.getContext(),
            "Nie udało się dodać zdjęcia!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSetPhotoNumber(int photoNumber) {
    this.photoNumber = photoNumber;
  }

  private class BackgroundActions extends AsyncTask<Void, Void, Void> {
    private Activity activity;

    private BackgroundActions(Activity activity) {
      this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
      activity.showDialog(DisplayRecipeActivity.PLEASE_WAIT_DIALOG);
      startBackgroundAddingPhoto(activity);
    }
    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        saved();
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }
    @Override
    protected void onPostExecute(Void result) {
      deleteAllSharedPreferences();
      recipeElementsView.navigateToMainCardsScreen();
      activity.removeDialog(DisplayRecipeActivity.PLEASE_WAIT_DIALOG);
      Toast.makeText(activity, "Zapisano!", Toast.LENGTH_SHORT).show();
    }
  }

  private class BackgroundAddingPhoto extends AsyncTask<Void, Void, Void> {
    private Activity activity;

    private BackgroundAddingPhoto(Activity activity) {
      this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
    }
    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        addPhotosNumberToElements();
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }
    @Override
    protected void onPostExecute(Void result) {
      Toast.makeText(activity, "Zdjęcia wysłane na serwer!", Toast.LENGTH_SHORT).show();
    }
  }
  
  private void sendPhotoToServer(String photo){
    recipeRepository.addPhoto(photo, this);
  }

  private void addPhotosNumberToElements() {
    addPhotoToRecipe();
    addPhotoToSteps();
  }

  private void addPhotoToRecipe(){
      for(Recipe recipe : recipeList){
          if(recipe.getMainPicture() != null){
              sendPhotoToServer(recipe.getMainPicture());
              while(photoNumber == -1){

              }
              Recipe recipeWithPhotoNumber = new Recipe(recipe.getName(), photoNumber,
                      recipe.getCategory(), recipe.getPrepareTime(), recipe.getCookTime(),
                      recipe.getBakeTime());
              recipeListWithPhotoNumber.add(recipeWithPhotoNumber);
              photoNumber = -1;
          }
          else {
              Recipe recipeWithPhotoNumber = new Recipe(recipe.getName(),
                      recipe.getCategory(), recipe.getPrepareTime(), recipe.getCookTime(),
                      recipe.getBakeTime());
              recipeListWithPhotoNumber.add(recipeWithPhotoNumber);
          }
      }
  }

  private void addPhotoToSteps(){
      for(Step step : stepList){
          if(step.getPhoto() != null){
              sendPhotoToServer(step.getPhoto());
              while(photoNumber == -1){

              }
              Step stepWithPhotoNumber = new Step(photoNumber, step.getStepNumber(),
                      step.getStepDescription());
              stepListWithPhotoNumber.add(stepWithPhotoNumber);
              photoNumber = -1;
          }
          else {
              Step stepWithPhotoNumber = new Step(photoNumber, step.getStepNumber(),
                      step.getStepDescription());
              stepListWithPhotoNumber.add(stepWithPhotoNumber);
          }
      }
  }

  private void addWholeElementsToServer() {
    Stars stars = new Stars(-1, 0, 0);
    starsList.add(stars);
    RecipeAllElements recipeAllElements =
            new RecipeAllElements(recipeListWithPhotoNumber, ingredientList,
                    stepListWithPhotoNumber, starsList);

    recipeRepository.addWholeRecipeElements(recipeAllElements, this);
  }
}
