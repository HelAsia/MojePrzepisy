package com.moje.przepisy.mojeprzepisy.timer;

import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public interface TimerContract {
  interface View{
    void setToolbar();
    void setListeners();
    void setFirstTimerActions(android.view.View view);
    void setSecondTimerActions(android.view.View view);
    void setThirdTimerActions(android.view.View view);
    NumberPicker getNumberPickerHour();
    NumberPicker getNumberPickerMinute();
    NumberPicker getNumberPickerSecond();
    TextView getFirstTimer();
    TextView getSecondTimer();
    TextView getThirdTimer();
    ImageView getFirstPlayTimer();
    ImageView getSecondPlayTimer();
    ImageView getThirdPlayTimer();
    ImageView getFirstPauseTimer();
    ImageView getSecondPauseTimer();
    ImageView getThirdPauseTimer();
    ImageView getFirstStopTimer();
    ImageView getSecondStopTimer();
    ImageView getThirdStopTimer();
  }

  interface Presenter{
    void setFirstIconEnabledStatus();
    void setMinAndMaxHourValueAndWheelSelector();
    void setMinAndMaxMinuteValueAndWheelSelector();
    void setMinAndMaxSecondValueAndWheelSelector();
    int getHour();
    int getMinute();
    int getSecond();
    void startFirstTimer();
    void startSecondTimer();
    void startThirdTimer();
    void pauseFirstTimer();
    void pauseSecondTimer();
    void pauseThirdTimer();
    void restartFirstTimer();
    void restartSecondTimer();
    void restartThirdTimer();
    void stopFirstTimer();
    void stopSecondTimer();
    void stopThirdTimer();
    int getCountDownTime();
  }
}