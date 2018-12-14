package com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
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
  private BitmapConverter converter = new BitmapConverter();
  private Gson gson = new Gson();
  private String recipeListPojo;

  public AddRecipePresenter(AddRecipeContract.View recipeView){
    this.recipeView = recipeView;
  }

  @Override
  public void setRecipe(List<Recipe> recipeList){
    this.recipeList = recipeList;
  }

  @Override
  public String convertPojoToJsonString(List<Recipe> recipeList) {
    Type type = new TypeToken<List<Recipe>>(){}.getType();
    return gson.toJson(recipeList, type);
  }

  public void setRecipeValueOnScreen(){
    setRecipe(getRecipeAfterChangeScreen(getPojoListFromFile(recipeView.getContext())));
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
      setRecipe(recipeList);
    }
  }

  @Override
  public Boolean checkIfValueIsEmpty() {
    if (recipeView.getRecipeNameEditText().getText().toString().equals("")){
      recipeView.getRecipeNameEditText().setHintTextColor(Color.parseColor("#ff3300"));
      recipeView.getRecipeNameEditText().setHint("Brak nazwy! Uzypełnij nazwę.");
      return true;
    }if(recipeView.getPreparedTimeEditText().getText().toString().equals("HH:MM")){
      recipeView.getPreparedTimeEditText().setText("00:00:00");
    }if(recipeView.getCookTimeEditText().getText().toString().equals("HH:MM")){
      recipeView.getCookTimeEditText().setText("00:00:00");
    }if (recipeView.getBakeTimeEditText().getText().toString().equals("HH:MM")){
      recipeView.getBakeTimeEditText().setText("00:00:00");
    }
    return false;
  }

  @Override
  public String getPojoListFromFile(Context context) {
    try {
      FileInputStream fileToRead = context.openFileInput("RecipeData.txt");
      StringBuffer fileToReadBuffer = new StringBuffer();
      BufferedReader dataIO = new BufferedReader(new InputStreamReader(fileToRead));

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

  @Override
  public List<Recipe> getRecipeAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Recipe>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  private class BackgroundSaveToFileActions extends AsyncTask<Void, Void, Void> {

    public BackgroundSaveToFileActions() {
    }

    @Override
    protected void onPreExecute() {
      int position = 0;
      recipeList.get(position).setName(recipeView.getRecipeNameEditText().getText().toString());
      BitmapDrawable drawable = (BitmapDrawable) recipeView.getMainPhotoImageView().getDrawable();
      Bitmap bitmap = drawable.getBitmap();
      recipeList.get(position).setMainPicture(converter.BitMapToString(bitmap));
      recipeList.get(position).setCategory((String) recipeView.getCategoryChooseSpinner().getSelectedItem());
      recipeList.get(position).setPrepareTime(recipeView.getPreparedTimeEditText().getText().toString());
      recipeList.get(position).setCookTime(recipeView.getCookTimeEditText().getText().toString());
      recipeList.get(position).setBakeTime(recipeView.getBakeTimeEditText().getText().toString());
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
