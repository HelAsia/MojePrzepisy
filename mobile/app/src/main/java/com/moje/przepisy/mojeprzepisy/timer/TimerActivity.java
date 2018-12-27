package com.moje.przepisy.mojeprzepisy.timer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.utils.Timer;

public class TimerActivity extends AppCompatActivity implements TimerContract.View{
  @BindView(R.id.toolbar_timer) Toolbar toolbar;
  @BindView(R.id.hourPicker) NumberPicker hourPicker;
  @BindView(R.id.minutePicker) NumberPicker minutePicker;
  @BindView(R.id.secondPicker) NumberPicker secondPicker;
  @BindView(R.id.timerOneTextView) TextView timerOneTextView;
  @BindView(R.id.timerTwoTextView) TextView timerTwoTextView;
  @BindView(R.id.timerThreeTextView) TextView timerThreeTextView;
  @BindView(R.id.playImageViewFirstTimer) ImageView playImageViewFirstTimer;
  @BindView(R.id.pauseImageViewFirstTimer) ImageView pauseImageViewFirstTimer;
  @BindView(R.id.stopImageViewFirstTimer) ImageView stopImageViewFirstTimer;
  @BindView(R.id.playImageViewSecondTimer) ImageView playImageViewSecondTimer;
  @BindView(R.id.pauseImageViewSecondTimer) ImageView pauseImageViewSecondTimer;
  @BindView(R.id.stopImageViewSecondTimer) ImageView stopImageViewSecondTimer;
  @BindView(R.id.playImageViewThirdTimer) ImageView playImageViewThirdTimer;
  @BindView(R.id.pauseImageViewThirdTimer) ImageView pauseImageViewThirdTimer;
  @BindView(R.id.stopImageViewThirdTimer) ImageView stopImageViewThirdTimer;
  private TimerContract.Presenter presenter;
  private Timer firstTimer;
  private Timer secondTimer;
  private Timer thirdTimer;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timer_view);
    ButterKnife.bind(this);

    presenter = new TimerPresenter(this);
    presenter.setFirstScreen();

    createTimerObjects();

  }

  @Override
  public void setToolbar() {
    toolbar.setSubtitle(R.string.set_timer);
    setSupportActionBar(toolbar);
  }

  @Override
  public void setListeners() {
    setFirstTimerActions();
    setSecondTimerActions();
    setThirdTimerActions();
  }

  private void createTimerObjects(){
    firstTimer = new Timer(playImageViewFirstTimer, pauseImageViewFirstTimer,
            stopImageViewFirstTimer, timerOneTextView);
    secondTimer = new Timer(playImageViewSecondTimer, pauseImageViewSecondTimer,
            stopImageViewSecondTimer, timerTwoTextView);
    thirdTimer = new Timer(playImageViewThirdTimer, pauseImageViewSecondTimer,
            stopImageViewSecondTimer, timerThreeTextView);
  }

  @Override
  public void setFirstIconEnabledStatus() {
    pauseImageViewFirstTimer.setEnabled(true);
    pauseImageViewSecondTimer.setEnabled(true);
    pauseImageViewThirdTimer.setEnabled(true);
    stopImageViewFirstTimer.setEnabled(true);
    stopImageViewSecondTimer.setEnabled(true);
    stopImageViewThirdTimer.setEnabled(true);
  }

  @Override
  public int getHour() {
    return hourPicker.getValue();
  }

  @Override
  public int getMinute() {
    return minutePicker.getValue();
  }

  @Override
  public int getSecond() {
    return secondPicker.getValue();
  }

  @Override
  public void setMinAndMaxHourValueAndWheelSelector() {
    hourPicker.setMinValue(0);
    hourPicker.setMaxValue(24);
    hourPicker.setWrapSelectorWheel(true);
  }

  @Override
  public void setMinAndMaxMinuteValueAndWheelSelector() {
    minutePicker.setMinValue(0);
    minutePicker.setMaxValue(59);
    minutePicker.setWrapSelectorWheel(true);
  }

  @Override
  public void setMinAndMaxSecondValueAndWheelSelector() {
    secondPicker.setMinValue(0);
    secondPicker.setMaxValue(59);
    secondPicker.setWrapSelectorWheel(true);
  }

  @Override
  public void setFirstTimerActions(){
    playImageViewFirstTimer.setOnClickListener(view -> {
      if(pauseImageViewFirstTimer.isEnabled()&& stopImageViewFirstTimer.isEnabled()){
        firstTimer.startTimer(presenter.getCountDownTime());
      }else{
        firstTimer.restartTimer();
      }
    });
    pauseImageViewFirstTimer.setOnClickListener(view -> firstTimer.pauseTimer());
    stopImageViewFirstTimer.setOnClickListener(view -> firstTimer.stopTimer());
  }

  @Override
  public void setSecondTimerActions() {
    playImageViewSecondTimer.setOnClickListener(view -> {
      if(pauseImageViewSecondTimer.isEnabled()&& stopImageViewSecondTimer.isEnabled()){
        secondTimer.startTimer(presenter.getCountDownTime());
      }else{
        secondTimer.restartTimer();
      }
    });
    pauseImageViewSecondTimer.setOnClickListener(view -> secondTimer.pauseTimer());
    stopImageViewSecondTimer.setOnClickListener(view -> secondTimer.stopTimer());
  }

  @Override
  public void setThirdTimerActions() {
    playImageViewThirdTimer.setOnClickListener(view -> {
      if(pauseImageViewThirdTimer.isEnabled()&& stopImageViewThirdTimer.isEnabled()){
        thirdTimer.startTimer(presenter.getCountDownTime());
      }else{
        thirdTimer.restartTimer();
      }
    });
    pauseImageViewThirdTimer.setOnClickListener(view -> thirdTimer.pauseTimer());
    stopImageViewThirdTimer.setOnClickListener(view -> thirdTimer.stopTimer());
  }
}
