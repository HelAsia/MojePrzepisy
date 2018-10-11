package com.moje.przepisy.mojeprzepisy.timer;

import android.os.CountDownTimer;

public class TimerPresenter implements TimerContract.Presenter{
  private TimerContract.View timerView;
  private CountDownTimer countDownTimerFirst;
  private CountDownTimer countDownTimerSecond;
  private CountDownTimer countDownTimerThird;
  private boolean isPausedFirstTimer = false;
  private boolean isCanceledFirstTimer = false;
  private boolean isPausedSecondTimer = false;
  private boolean isCanceledSecondTimer = false;
  private boolean isPausedThirdTimer = false;
  private boolean isCanceledThirdTimer = false;
  private long timeRemainingFirstTimer = 0;
  private long timeRemainingSecondTimer = 0;
  private long timeRemainingThirdTimer = 0;

  public TimerPresenter(TimerContract.View timerView){
    this.timerView = timerView;
  }

  @Override
  public void setFirstIconEnabledStatus() {
    timerView.getFirstPauseTimer().setEnabled(true);
    timerView.getSecondPauseTimer().setEnabled(true);
    timerView.getThirdPauseTimer().setEnabled(true);
    timerView.getFirstStopTimer().setEnabled(true);
    timerView.getSecondStopTimer().setEnabled(true);
    timerView.getThirdStopTimer().setEnabled(true);
  }

  @Override
  public void setMinAndMaxHourValueAndWheelSelector() {
    timerView.getNumberPickerHour().setMinValue(0);
    timerView.getNumberPickerHour().setMaxValue(24);
    timerView.getNumberPickerHour().setWrapSelectorWheel(true);
  }

  @Override
  public void setMinAndMaxMinuteValueAndWheelSelector() {
    timerView.getNumberPickerMinute().setMinValue(0);
    timerView.getNumberPickerMinute().setMaxValue(59);
    timerView.getNumberPickerMinute().setWrapSelectorWheel(true);
  }

  @Override
  public void setMinAndMaxSecondValueAndWheelSelector() {
    timerView.getNumberPickerSecond().setMinValue(0);
    timerView.getNumberPickerSecond().setMaxValue(59);
    timerView.getNumberPickerSecond().setWrapSelectorWheel(true);
  }

  @Override
  public int getHour() {
    return timerView.getNumberPickerHour().getValue();
  }

  @Override
  public int getMinute() {
    return timerView.getNumberPickerMinute().getValue();
  }

  @Override
  public int getSecond() {
    return timerView.getNumberPickerSecond().getValue();
  }

  private String formatNumber(long value){
    if(value < 10)
      return "0" + value;
    return value + "";
  }

  @Override
  public void startFirstTimer() {
    isPausedFirstTimer = false;
    isCanceledFirstTimer = false;
    timerView.getFirstPlayTimer().setEnabled(false);
    timerView.getFirstPauseTimer().setEnabled(true);
    timerView.getFirstStopTimer().setEnabled(true);

    countDownTimerFirst = new CountDownTimer(getCountDownTime(), 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(isPausedFirstTimer||isCanceledFirstTimer){
          cancel();
        }else {
          if(minutes > 0)
            seconds = seconds % 60;
          if(hours > 0)
            minutes = minutes % 60;
          String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
              formatNumber(seconds);
          timerView.getFirstTimer().setText(time);

          timeRemainingFirstTimer = millisUntilFinished;
        }
      }

      @Override
      public void onFinish() {
        timerView.getFirstTimer().setText("DONE");
        timerView.getFirstPlayTimer().setEnabled(true);
        timerView.getFirstPauseTimer().setEnabled(false);
        timerView.getFirstStopTimer().setEnabled(false);
      }
    }.start();
  }

  @Override
  public void startSecondTimer() {
    isPausedSecondTimer = false;
    isCanceledSecondTimer = false;
    timerView.getSecondPlayTimer().setEnabled(false);
    timerView.getSecondPauseTimer().setEnabled(true);
    timerView.getSecondStopTimer().setEnabled(true);


    countDownTimerSecond = new CountDownTimer(getCountDownTime(), 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(isPausedSecondTimer||isCanceledSecondTimer){
          cancel();
        }else {
          if(minutes > 0)
            seconds = seconds % 60;
          if(hours > 0)
            minutes = minutes % 60;
          String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
              formatNumber(seconds);
          timerView.getSecondTimer().setText(time);

          timeRemainingSecondTimer = millisUntilFinished;
        }
      }

      @Override
      public void onFinish() {
        timerView.getSecondTimer().setText("DONE");
        timerView.getSecondPlayTimer().setEnabled(true);
        timerView.getSecondPauseTimer().setEnabled(false);
        timerView.getSecondStopTimer().setEnabled(false);
      }
    }.start();
  }

  @Override
  public void startThirdTimer() {
    isPausedThirdTimer = false;
    isCanceledThirdTimer = false;
    timerView.getFirstPlayTimer().setEnabled(false);
    timerView.getThirdPauseTimer().setEnabled(true);
    timerView.getThirdStopTimer().setEnabled(true);

    countDownTimerThird = new CountDownTimer(getCountDownTime(), 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(isPausedThirdTimer||isCanceledThirdTimer){
          cancel();
        }else {
          if(minutes > 0)
            seconds = seconds % 60;
          if(hours > 0)
            minutes = minutes % 60;
          String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
              formatNumber(seconds);
          timerView.getThirdTimer().setText(time);

          timeRemainingThirdTimer = millisUntilFinished;
        }
      }

      @Override
      public void onFinish() {
        timerView.getThirdTimer().setText("DONE");
        timerView.getThirdPlayTimer().setEnabled(true);
        timerView.getThirdPauseTimer().setEnabled(false);
        timerView.getThirdStopTimer().setEnabled(false);
      }
    }.start();
  }

  @Override
  public void pauseFirstTimer() {
    isPausedFirstTimer = true;

    timerView.getFirstPlayTimer().setEnabled(true);
    timerView.getFirstPauseTimer().setEnabled(false);
    timerView.getFirstStopTimer().setEnabled(false);
  }

  @Override
  public void pauseSecondTimer() {
    isPausedSecondTimer = true;

    timerView.getSecondPlayTimer().setEnabled(true);
    timerView.getSecondPauseTimer().setEnabled(false);
    timerView.getSecondStopTimer().setEnabled(false);
  }

  @Override
  public void pauseThirdTimer() {
    isPausedThirdTimer = true;

    timerView.getThirdPlayTimer().setEnabled(true);
    timerView.getThirdPauseTimer().setEnabled(false);
    timerView.getThirdStopTimer().setEnabled(false);
  }

  @Override
  public void restartFirstTimer() {
    isPausedFirstTimer = false;
    isCanceledFirstTimer = false;

    timerView.getFirstPlayTimer().setEnabled(false);
    timerView.getFirstPauseTimer().setEnabled(true);
    timerView.getFirstStopTimer().setEnabled(true);

    countDownTimerFirst = new CountDownTimer(timeRemainingFirstTimer, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(isPausedFirstTimer||isCanceledFirstTimer){
          cancel();
        }else {
          if(minutes > 0)
            seconds = seconds % 60;
          if(hours > 0)
            minutes = minutes % 60;
          String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
              formatNumber(seconds);
          timerView.getFirstTimer().setText(time);

          timeRemainingFirstTimer = millisUntilFinished;
        }
      }

      @Override
      public void onFinish() {
        timerView.getFirstTimer().setText("DONE");
        timerView.getFirstPlayTimer().setEnabled(true);
        timerView.getFirstPauseTimer().setEnabled(false);
        timerView.getFirstStopTimer().setEnabled(false);
      }
    }.start();
  }

  @Override
  public void restartSecondTimer() {
    isPausedSecondTimer = false;
    isCanceledSecondTimer = false;

    timerView.getSecondPlayTimer().setEnabled(false);
    timerView.getSecondPauseTimer().setEnabled(true);
    timerView.getSecondStopTimer().setEnabled(true);

    countDownTimerSecond = new CountDownTimer(timeRemainingSecondTimer, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(isPausedSecondTimer||isCanceledSecondTimer){
          cancel();
        }else {
          if(minutes > 0)
            seconds = seconds % 60;
          if(hours > 0)
            minutes = minutes % 60;
          String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
              formatNumber(seconds);
          timerView.getSecondTimer().setText(time);

          timeRemainingSecondTimer = millisUntilFinished;
        }
      }

      @Override
      public void onFinish() {
        timerView.getSecondTimer().setText("DONE");
        timerView.getSecondPlayTimer().setEnabled(true);
        timerView.getSecondPauseTimer().setEnabled(false);
        timerView.getSecondStopTimer().setEnabled(false);
      }
    }.start();
  }

  @Override
  public void restartThirdTimer() {
    isPausedThirdTimer = false;
    isCanceledThirdTimer = false;

    timerView.getSecondPlayTimer().setEnabled(false);
    timerView.getSecondPauseTimer().setEnabled(true);
    timerView.getSecondStopTimer().setEnabled(true);

    countDownTimerSecond = new CountDownTimer(timeRemainingThirdTimer, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(isPausedThirdTimer||isCanceledThirdTimer){
          cancel();
        }else {
          if(minutes > 0)
            seconds = seconds % 60;
          if(hours > 0)
            minutes = minutes % 60;
          String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
              formatNumber(seconds);
          timerView.getThirdTimer().setText(time);

          timeRemainingThirdTimer = millisUntilFinished;
        }
      }

      @Override
      public void onFinish() {
        timerView.getThirdTimer().setText("DONE");
        timerView.getThirdPlayTimer().setEnabled(true);
        timerView.getThirdPauseTimer().setEnabled(false);
        timerView.getThirdStopTimer().setEnabled(false);
      }
    }.start();
  }

  @Override
  public void stopFirstTimer() {
    isCanceledFirstTimer = true;

    timerView.getFirstPlayTimer().setEnabled(true);
    timerView.getFirstPauseTimer().setEnabled(false);
    timerView.getFirstStopTimer().setEnabled(false);

    timerView.getFirstTimer().setText("00:00:00");
  }

  @Override
  public void stopSecondTimer() {
    isCanceledSecondTimer = true;

    timerView.getSecondPlayTimer().setEnabled(true);
    timerView.getSecondPauseTimer().setEnabled(false);
    timerView.getSecondStopTimer().setEnabled(false);

    timerView.getSecondTimer().setText("00:00:00");
  }

  @Override
  public void stopThirdTimer() {
    isCanceledThirdTimer = true;

    timerView.getThirdPlayTimer().setEnabled(true);
    timerView.getThirdPauseTimer().setEnabled(false);
    timerView.getThirdStopTimer().setEnabled(false);

    timerView.getThirdTimer().setText("00:00:00");
  }

  @Override
  public int getCountDownTime() {
    return (getHour()*3600 + getMinute()*60 + getSecond())*1000;
  }
}
