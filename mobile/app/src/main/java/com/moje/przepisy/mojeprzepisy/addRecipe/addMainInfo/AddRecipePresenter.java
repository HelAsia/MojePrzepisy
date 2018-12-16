package com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddRecipePresenter implements AddRecipeContract.Presenter{
  private AddRecipeContract.View recipeView;
  private List<Recipe> recipeList = new ArrayList<>();
  private Gson gson = new Gson();

  public AddRecipePresenter(AddRecipeContract.View recipeView){
    this.recipeView = recipeView;
  }

  private String convertPojoToJsonString(List<Recipe> recipeList) {
    Type type = new TypeToken<List<Recipe>>(){}.getType();
    return gson.toJson(recipeList, type);
  }

  public void setRecipeValueOnScreen(){
    setRecipeList(getRecipeAfterChangeScreen(getPojoListFromFile(recipeView.getContext())));
    recipeView.setMainPhotoImageView(recipeList.get(0).getMainPicture());
    recipeView.setRecipeNameEditText(recipeList.get(0).getName());
    recipeView.setCategoryChooseSpinner(recipeList.get(0).getCategory());
    recipeView.setPreparedTimeEditText(recipeList.get(0).getPrepareTime());
    recipeView.setCookTimeEditText(recipeList.get(0).getCookTime());
    recipeView.setBakeTimeEditText(recipeList.get(0).getBakeTime());
  }

  public void setRecipeValueInFile(){
    new BackgroundSaveToFileActions().execute();
  }

  public void setFirstScreen(){
    List<Recipe> recipeFirstList = getRecipeAfterChangeScreen(getPojoListFromFile(recipeView.getContext()));
    if(recipeFirstList != null){
      recipeList = recipeFirstList;
      setRecipeValueOnScreen();
    }else {
      Recipe recipe = new Recipe("Nazwa przepisu", "https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", "Przekąski", "00:00:00", "00:00:00", "00:00:00");
      recipeList.add(recipe);
      setRecipeList(recipeList);
    }
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

  private String getPojoListFromFile(Context context) {
    try {
      FileInputStream fileToRead = context.openFileInput("RecipeData.txt");
      StringBuffer fileToReadBuffer = new StringBuffer();
      BufferedReader dataIO = new BufferedReader(new InputStreamReader(fileToRead));

      String recipeListPojo;
      while ((recipeListPojo = dataIO.readLine()) != null){
        fileToReadBuffer.append(recipeListPojo + "\n");
      }

      dataIO.close();
      fileToRead.close();
      return fileToReadBuffer.toString();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Recipe> getRecipeAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Recipe>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  private class BackgroundSaveToFileActions extends AsyncTask<Void, Void, Void> {

    public BackgroundSaveToFileActions() {
    }

    @Override
    protected void onPreExecute() {
      setRecipeList();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        FileOutputStream fileWithData;
        try {
          fileWithData = recipeView.getContext().openFileOutput("RecipeData.txt", Context.MODE_PRIVATE);
          try {
            Log.d("STRING TO WRITE", convertPojoToJsonString(recipeList));
            fileWithData.write(convertPojoToJsonString(recipeList).getBytes());
            fileWithData.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }

        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      Toast.makeText(recipeView.getContext(), "DODANE", Toast.LENGTH_SHORT).show();
    }
  }
}
