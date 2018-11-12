package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Photo;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.RecipeAllElements;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DisplayAllRecipeElementsPresenter implements DisplayAllRecipeElementsContract.Presenter,
    RecipeRepository.OnWholeRecipeElementsFinishedListener, RecipeRepository.OnAddPhotoFinishedListener{
  private RecipeRepository recipeRepository;
  private DisplayAllRecipeElementsContract.View recipeElementsView;
  private List<Ingredient> ingredientList = new ArrayList<>();
  private List<Recipe> recipeList = new ArrayList<>();
  private List<Step> stepList = new ArrayList<>();
  private List<Recipe> recipeListWithPhotoNumber = new ArrayList<>();
  private List<Step> stepListWithPhotoNumber = new ArrayList<>();
  private List<Stars> starsList = new ArrayList<>();
  private Gson gson = new Gson();
  private int photoNumber = -1;
  private Boolean isWholeRecipeAdded = false;

  public DisplayAllRecipeElementsPresenter(DisplayAllRecipeElementsContract.View recipeElementsView, RecipeRepository recipeRepository){
    this.recipeElementsView = recipeElementsView;
    this.recipeRepository = recipeRepository;
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

  @Override
  public void setRecipeList(List<Recipe> recipeList) {
    this.recipeList = recipeList;
  }

  @Override
  public List<Ingredient> getIngredientList(){
    return ingredientList;
  }

  @Override
  public void setIngredientList(List<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }

  @Override
  public List<Step> getStepList() {
    return stepList;
  }

  @Override
  public void setStepList(List<Step> stepList) {
    this.stepList = stepList;
  }


  @Override
  public String getIngredientsPojoListFromPreferences(Context context) {
    return PreferenceManager
        .getDefaultSharedPreferences(context).getString(Constant.PREF_INGREDIENT, null);
  }

  @Override
  public List<Ingredient> getIngredientListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Ingredient>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public String getRecipeListPojoFromPreferences(Context context) {
    return PreferenceManager
        .getDefaultSharedPreferences(context).getString(Constant.PREF_RECIPE, null);
  }

  @Override
  public List<Recipe> getRecipeListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Recipe>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public String getStepsPojoListFromPreferences(Context context) {
    return PreferenceManager
        .getDefaultSharedPreferences(context).getString(Constant.PREF_STEP, null);
  }

  @Override
  public List<Step> getStepListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Step>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void deleteAllSharedPreferences() {
    SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(recipeElementsView.getContext()).edit();
    ingredientsEditor.remove(Constant.PREF_INGREDIENT);
    ingredientsEditor.apply();
    SharedPreferences.Editor stepsEditor = PreferenceManager.getDefaultSharedPreferences(recipeElementsView.getContext()).edit();
    stepsEditor.remove(Constant.PREF_STEP);
    stepsEditor.apply();
    SharedPreferences.Editor recipeEditor = PreferenceManager.getDefaultSharedPreferences(recipeElementsView.getContext()).edit();
    recipeEditor.remove(Constant.PREF_RECIPE);
    recipeEditor.apply();
  }

  @Override
  public void setRecipeDetailsScreen() {
    recipeList = getRecipeListAfterChangeScreen(getRecipeListPojoFromPreferences(recipeElementsView.getContext()));

    if(recipeList != null){
      setRecipeList(recipeList);
      recipeElementsView.setRecipeRecyclerView(recipeList);
    }else {
      Toast.makeText(recipeElementsView.getContext(), "Nie udało się pobrać głównych informacji o przepisie!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void setIngredientsDetailScreen() {
    ingredientList = getIngredientListAfterChangeScreen(getIngredientsPojoListFromPreferences(recipeElementsView.getContext()));

    if(ingredientList != null){
      setIngredientList(ingredientList);
      recipeElementsView.setIngredientsRecyclerView(ingredientList);
    }else {
      Toast.makeText(recipeElementsView.getContext(), "Nie udało się pobrać składników!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void setStepsDetailsScreen() {
    stepList = getStepListAfterChangeScreen(getStepsPojoListFromPreferences(recipeElementsView.getContext()));

    if(stepList != null){
      setStepList(stepList);
      recipeElementsView.setStepsRecyclerView(stepList);
    }else {
      Toast.makeText(recipeElementsView.getContext(), "Nie udało się pobrać kroków!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void saved() {
    addWholeElementsToServer();
    Log.i("DisplayAllRecipeElementsPresenter.saved():", "After addWholeElementsToServer()");
    while (!isWholeRecipeAdded){

    }
    Log.i("DisplayAllRecipeElementsPresenter.saved():", "After while (!isWholeRecipeAdded)");
  }

  @Override
  public void onRecipeError() {
    if(recipeElementsView != null){
      recipeElementsView.getInformationTextView().setText("Wystąpił błąd podczas dodawania przepisu. Spróbuj ponownie.");
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

  @Override
  public void startBackgroundAddingPhoto(Activity activity){
    new BackgroundAddingPhoto(activity).execute();
  }


  @Override
  public void onPhotoError() {
    Toast.makeText(recipeElementsView.getContext(), "Nie udało się dodać zdjęcia!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSetPhotoNumber(int photoNumber) {
    this.photoNumber = photoNumber;
  }

  private class BackgroundActions extends AsyncTask<Void, Void, Void> {
    private Activity activity;

    public BackgroundActions(Activity activity) {
      this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
      activity.showDialog(DisplayAllRecipeElementsActivityView.PLEASE_WAIT_DIALOG);
      startBackgroundAddingPhoto(activity);
    }
    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        Log.i("BackgroundActions.doInBackground():", "Before saved()");
        saved();
        Log.i("BackgroundActions.doInBackground():", "After saved()");
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
      activity.removeDialog(DisplayAllRecipeElementsActivityView.PLEASE_WAIT_DIALOG);
      Toast.makeText(activity, "Zapisano!", Toast.LENGTH_SHORT).show();
    }
  }

  private class BackgroundAddingPhoto extends AsyncTask<Void, Void, Void> {
    private Activity activity;

    public BackgroundAddingPhoto(Activity activity) {
      this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
    }
    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        Log.i("BackgroundActions.doInBackground():", "Before saved()");
        addPhotosNumberToElements();
        Log.i("BackgroundActions.doInBackground():", "After saved()");
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

  @Override
  public void sendPhotoToServer(String photo){
 //   Photo photoToSend = new Photo(photo);
    recipeRepository.addPhoto(photo, this);
  }

  @Override
  public void addPhotosNumberToElements() {
    for(Recipe recipe : recipeList){
      sendPhotoToServer(recipe.getRecipeMainPicture());
      while(photoNumber == -1){

      }
      Recipe recipeWithPhotoNumber = new Recipe(recipe.getRecipeName(), photoNumber,
          recipe.getRecipeCategory(), recipe.getRecipePrepareTime(), recipe.getRecipeCookTime(),
          recipe.getRecipeBakeTime());
      Log.d("addPhotosNumberToElements()", "After added recipe photo. ");
      recipeListWithPhotoNumber.add(recipeWithPhotoNumber);
      photoNumber = -1;
    }

    for(Step step : stepList){
      sendPhotoToServer(step.getPhoto());
      while(photoNumber == -1){

      }
      Step stepWithPhotoNumber = new Step(photoNumber, step.getStepNumber(),
          step.getStepDescription());
      Log.d("addPhotosNumberToElements()", "After added step photo. ");
      stepListWithPhotoNumber.add(stepWithPhotoNumber);
      photoNumber = -1;
    }
  }


  @Override
  public void addWholeElementsToServer() {
    Stars stars = new Stars(-1, 0, 0);
    Log.i("DisplayAllRecipeElementsPresenter.addWholeElementsToServer():", "After created new Stars Object");
    starsList.add(stars);
    Log.i("DisplayAllRecipeElementsPresenter.addWholeElementsToServer():", "After added new Stars Object to starsList");
    RecipeAllElements recipeAllElements = new RecipeAllElements(recipeListWithPhotoNumber, ingredientList, stepListWithPhotoNumber, starsList);

    recipeRepository.addWholeRecipeElements(recipeAllElements, this);
    Log.i("DisplayAllRecipeElementsPresenter.addWholeElementsToServer():", "After addWholeRecipeElements()");
  }
}
