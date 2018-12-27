package com.moje.przepisy.mojeprzepisy.timer;

public class TimerPresenter implements TimerContract.Presenter{
  private TimerContract.View timerView;

  TimerPresenter(TimerContract.View timerView){
    this.timerView = timerView;
  }

  @Override
  public void setFirstScreen() {
    timerView.setToolbar();
    timerView.setListeners();
    timerView.setFirstIconEnabledStatus();;
    timerView.setMinAndMaxHourValueAndWheelSelector();
    timerView.setMinAndMaxMinuteValueAndWheelSelector();
    timerView.setMinAndMaxSecondValueAndWheelSelector();
  }

  @Override
  public int getCountDownTime() {
    return (timerView.getHour()*3600 + timerView.getMinute()*60 + timerView.getSecond())*1000;
  }
}
