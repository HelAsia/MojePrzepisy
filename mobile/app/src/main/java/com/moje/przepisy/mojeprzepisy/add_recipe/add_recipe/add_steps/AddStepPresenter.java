package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddStepPresenter implements AddStepContract.Presenter {
  private AddStepContract.View stepsView;
  private List<Step> stepList = new ArrayList<>();
  private Gson gson = new Gson();
  private int stepNumber = -1;

  AddStepPresenter(AddStepContract.View stepsView){
    this.stepsView = stepsView;
  }

  @Override
  public String convertPojoToJsonString(List<Step> step) {
    Type type = new TypeToken<List<Step>>(){}.getType();
    return gson.toJson(step, type);
  }

  @Override
  public void addPojoListToFile() {
    new BackgroundSaveStepsToFileActions().execute();
  }

  @Override
  public String getPojoListFromFile(Context context) {
    try {
      FileInputStream fileToRead = context.openFileInput("StepsData.txt");
      StringBuffer fileToReadBuffer = new StringBuffer();
      BufferedReader dataIO = new BufferedReader(new InputStreamReader(fileToRead));

      String stepsListPojo;
      while ((stepsListPojo = dataIO.readLine()) != null){
        fileToReadBuffer.append(stepsListPojo + "\n");
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
  public List<Step> getStepListAfterChangeScreen(String jsonList) {
    Type type = new TypeToken<List<Step>>() {}.getType();
    return gson.fromJson(jsonList, type);
  }

  @Override
  public void setFirstScreen() {
    List<Step> stepFirstList = getStepListAfterChangeScreen(getPojoListFromFile(stepsView.getContext()));
    if(stepFirstList != null){
      stepList = stepFirstList;
      stepsView.setRecyclerView(stepList);
    }else {
      Step emptyStep = new Step( -1, "Opis kroku");
      stepList.add(emptyStep);
      stepsView.setRecyclerView(stepList);
    }
  }

  @Override
  public void setNextStep() {
    for (Step step : stepList){
      if(step.getStepNumber() > stepNumber){
        stepNumber = step.getStepNumber();
      }
    }
    Step emptyStep = new Step(stepNumber, "Opis kroku");

    stepList = getStepListAfterChangeScreen(getPojoListFromFile(stepsView.getContext()));
    stepList.add(emptyStep);
    stepsView.setRecyclerView(stepList);

    addPojoListToFile();
  }

  private class BackgroundSaveStepsToFileActions extends AsyncTask<Void, Void, Void> {

    public BackgroundSaveStepsToFileActions() {
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        FileOutputStream fileWithData;
        try {
          fileWithData = (stepsView.getContext().openFileOutput("StepsData.txt", Context.MODE_PRIVATE));
          try {
            Log.d("STRING TO WRITE", convertPojoToJsonString(stepList));
            fileWithData.write(convertPojoToJsonString(stepList).getBytes());
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
      Toast.makeText(stepsView.getContext(), "DODANE", Toast.LENGTH_SHORT).show();
    }
  }
}
