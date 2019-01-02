package com.moje.przepisy.mojeprzepisy.timer;

public interface TimerContract {
  interface View{
    void setToolbar();
    void setListeners();
    void setFirstIconEnabledStatus();
    int getHour();
    int getMinute();
    int getSecond();
    void setMinAndMaxHourValueAndWheelSelector();
    void setMinAndMaxMinuteValueAndWheelSelector();
    void setMinAndMaxSecondValueAndWheelSelector();
    void setFirstTimerActions();
    void setSecondTimerActions();
    void setThirdTimerActions();
  }

  interface Presenter{
    void setFirstScreen();
    int getCountDownTime();
  }
}