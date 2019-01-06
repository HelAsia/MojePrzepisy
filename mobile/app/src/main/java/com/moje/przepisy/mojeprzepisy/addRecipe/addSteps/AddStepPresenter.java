package com.moje.przepisy.mojeprzepisy.addRecipe.addSteps;

import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.moje.przepisy.mojeprzepisy.utils.PojoFileConverter;
import com.moje.przepisy.mojeprzepisy.utils.PojoJsonConverter;
import java.util.ArrayList;
import java.util.List;

public class AddStepPresenter implements AddStepContract.Presenter {
  private AddStepContract.View stepsView;
  private List<Step> stepList = new ArrayList<>();
  private PojoFileConverter pojoFileConverter;
  private PojoJsonConverter pojoJsonConverter = new PojoJsonConverter();

  AddStepPresenter(AddStepContract.View stepsView){
    this.stepsView = stepsView;
    pojoFileConverter = new PojoFileConverter(stepsView.getContext());
  }

  @Override
  public void previousAction(){
    pojoFileConverter.addPojoListToFile(Constant.STEPS_FILE_NAME, stepList);
    stepsView.navigateToPreviousPage();
  }

  @Override
  public void nextAction() {
    pojoFileConverter.addPojoListToFile(Constant.STEPS_FILE_NAME, stepList);
    stepsView.navigateToNextPage();
  }

  private void setFirstList(){
    List<Step> stepFirstList = getStepFirstList();

    if(stepFirstList != null){
      stepList = stepFirstList;
      stepsView.setRecyclerView(stepList);
    }else {
      setInitialStep();
    }
  }

  @Override
  public void setFirstScreen() {
    stepsView.setToolbar();
    stepsView.setListeners();
    setFirstList();
  }

  @Override
  public void setNextStep() {
    setInitialStep();
    pojoFileConverter.addPojoListToFile(Constant.STEPS_FILE_NAME, stepList);
  }

  private void setInitialStep(){
    Step emptyStep = new Step(1, "Opis kroku");
    stepList.add(emptyStep);
    stepsView.setRecyclerView(stepList);
  }

  private List<Step> getStepFirstList(){
    return pojoJsonConverter.convertJsonToPojo(pojoFileConverter
            .getPojoListFromFile(Constant.STEPS_FILE_NAME), Constant.STEPS_FILE_NAME);
  }
}
