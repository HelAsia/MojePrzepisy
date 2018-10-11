package com.moje.przepisy.mojeprzepisy.timer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;

public class TimerActivityView extends AppCompatActivity implements TimerContract.View,
    View.OnClickListener{
  Context context;
  private TimerContract.Presenter presenter;
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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timer_view);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new TimerPresenter(this);

    presenter.setFirstIconEnabledStatus();
    presenter.setMinAndMaxHourValueAndWheelSelector();
    presenter.setMinAndMaxMinuteValueAndWheelSelector();
    presenter.setMinAndMaxSecondValueAndWheelSelector();

    setListeners();
  }

  @Override
  public void onClick(View view) {
      if(view.getId() == R.id.playImageViewFirstTimer){
        if(pauseImageViewFirstTimer.isEnabled()&&stopImageViewFirstTimer.isEnabled()){
          presenter.startFirstTimer();
        }else{
          presenter.restartFirstTimer();
        }
      }else if(view.getId() == R.id.pauseImageViewFirstTimer){
        presenter.pauseFirstTimer();
      }else if(view.getId() == R.id.stopImageViewFirstTimer){
        presenter.stopFirstTimer();
      }else if(view.getId() == R.id.playImageViewSecondTimer){
        if(pauseImageViewSecondTimer.isEnabled()&& stopImageViewSecondTimer.isEnabled()){
          presenter.startSecondTimer();
        }else{
          presenter.restartSecondTimer();
        }
      }else if(view.getId() == R.id.pauseImageViewSecondTimer){
        presenter.pauseSecondTimer();
      }else if(view.getId() == R.id.stopImageViewSecondTimer){
        presenter.stopSecondTimer();
      }else if(view.getId() == R.id.playImageViewThirdTimer){
        if(pauseImageViewThirdTimer.isEnabled()&& stopImageViewThirdTimer.isEnabled()){
          presenter.startThirdTimer();
        }else{
          presenter.restartThirdTimer();
        }
      }else if(view.getId() == R.id.pauseImageViewThirdTimer){
        presenter.pauseThirdTimer();
      }else if(view.getId() == R.id.stopImageViewThirdTimer){
        presenter.stopThirdTimer();
      }
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_timer);
    toolbar.setSubtitle(R.string.set_timer);
    setSupportActionBar(toolbar);
  }

  @Override
  public void setListeners() {
    playImageViewFirstTimer.setOnClickListener(this);
    pauseImageViewFirstTimer.setOnClickListener(this);
    stopImageViewFirstTimer.setOnClickListener(this);
    playImageViewSecondTimer.setOnClickListener(this);
    pauseImageViewSecondTimer.setOnClickListener(this);
    stopImageViewSecondTimer.setOnClickListener(this);
    playImageViewThirdTimer.setOnClickListener(this);
    pauseImageViewThirdTimer.setOnClickListener(this);
    stopImageViewThirdTimer.setOnClickListener(this);
  }

  @Override
  public NumberPicker getNumberPickerHour() {
    return hourPicker;
  }

  @Override
  public NumberPicker getNumberPickerMinute() {
    return minutePicker;
  }

  @Override
  public NumberPicker getNumberPickerSecond() {
    return secondPicker;
  }

  @Override
  public TextView getFirstTimer() {
    return timerOneTextView;
  }

  @Override
  public TextView getSecondTimer() {
    return timerTwoTextView;
  }

  @Override
  public TextView getThirdTimer() {
    return timerThreeTextView;
  }

  @Override
  public ImageView getFirstPlayTimer() {
    return playImageViewFirstTimer;
  }

  @Override
  public ImageView getSecondPlayTimer() {
    return playImageViewSecondTimer;
  }

  @Override
  public ImageView getThirdPlayTimer() {
    return playImageViewThirdTimer;
  }

  @Override
  public ImageView getFirstPauseTimer() {
    return pauseImageViewFirstTimer;
  }

  @Override
  public ImageView getSecondPauseTimer() {
    return pauseImageViewSecondTimer;
  }

  @Override
  public ImageView getThirdPauseTimer() {
    return pauseImageViewThirdTimer;
  }

  @Override
  public ImageView getFirstStopTimer() {
    return stopImageViewFirstTimer;
  }

  @Override
  public ImageView getSecondStopTimer() {
    return stopImageViewSecondTimer;
  }

  @Override
  public ImageView getThirdStopTimer() {
    return stopImageViewThirdTimer;
  }
}
