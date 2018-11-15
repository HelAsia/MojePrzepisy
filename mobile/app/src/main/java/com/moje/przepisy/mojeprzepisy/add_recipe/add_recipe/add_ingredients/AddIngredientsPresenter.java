package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddIngredientsPresenter implements AddIngredientsContract.Presenter {
  private AddIngredientsContract.View ingredientsView;
  private List<Ingredient> ingredientList = new ArrayList<>();
  private Gson gson = new Gson();

  public AddIngredientsPresenter(AddIngredientsContract.View ingredientsView){
    this.ingredientsView = ingredientsView;
  }

  @Override
  public void setIngredientList(List<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }

  @Override
  public String convertPojoToJsonString(List<Ingredient> ingredientList) {
    Type type = new TypeToken<List<Ingredient>>(){}.getType();
    return gson.toJson(ingredientList, type);
  }

  @Override
  public void addPojoListToFile() {
    new BackgroundSaveIngredientsToFileActions().execute();
  }

  @Override
  public String getPojoListFromFile(Context context) {
    try {
      FileInputStream fileToRead = context.openFileInput("IngredientsData.txt");
      StringBuffer fileToReadBuffer = new StringBuffer();
      BufferedReader dataIO = new BufferedReader(new InputStreamReader(fileToRead));

      String ingredientsListPojo;
      while ((ingredientsListPojo = dataIO.readLine()) != null){
        fileToReadBuffer.append(ingredientsListPojo + "\n");
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
  public List<Ingredient> getIngredientListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Ingredient>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void setFirstScreen() {
    List<Ingredient> ingredientFirstList = getIngredientListAfterChangeScreen(getPojoListFromFile(ingredientsView.getContext()));
    if(ingredientFirstList != null){
      ingredientList = ingredientFirstList;
      setIngredientList(ingredientList);
      ingredientsView.setRecyclerView(ingredientList);
    }else {
      Ingredient emptyIngredient = new Ingredient(1, "kg", "np. cukier");
      ingredientList.add(emptyIngredient);
      setIngredientList(ingredientList);
      ingredientsView.setRecyclerView(ingredientList);
    }
  }

  @Override
  public void setNextStep() {
    Ingredient emptyIngredient = new Ingredient(1, "kg", "np. cukier");

    ingredientList.add(emptyIngredient);
    setIngredientList(ingredientList);
    ingredientsView.setRecyclerView(ingredientList);

    addPojoListToFile();
  }

  private class BackgroundSaveIngredientsToFileActions extends AsyncTask<Void, Void, Void> {

    public BackgroundSaveIngredientsToFileActions() {
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        FileOutputStream fileWithData;
        try {
          fileWithData = (ingredientsView.getContext().openFileOutput("IngredientsData.txt", Context.MODE_PRIVATE));
          try {
            Log.d("STRING TO WRITE", convertPojoToJsonString(ingredientList));
            fileWithData.write(convertPojoToJsonString(ingredientList).getBytes());
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
      Toast.makeText(ingredientsView.getContext(), "DODANE", Toast.LENGTH_SHORT).show();
    }
  }
}

