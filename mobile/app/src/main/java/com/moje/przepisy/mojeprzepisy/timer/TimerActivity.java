package com.moje.przepisy.mojeprzepisy.timer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import com.moje.przepisy.mojeprzepisy.R;

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
  @BindView(R.id.progressBar1) RingProgressBar progressBar1;
  @BindView(R.id.progressBar2) RingProgressBar progressBar2;
  @BindView(R.id.progressBar3) RingProgressBar progressBar3;
  private TimerContract.Presenter presenter;
  private Timer firstTimer;
  private Timer secondTimer;
  private Timer thirdTimer;
  private NotificationManager notificationManager;
  private NotificationCompat.Builder notificationBuilder;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timer_view);
    ButterKnife.bind(this);

    presenter = new TimerPresenter(this);
    presenter.setFirstScreen();

    createTimerObjects();
    notificationManager = (NotificationManager) this
            .getSystemService(Context.NOTIFICATION_SERVICE);

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
            stopImageViewFirstTimer, timerOneTextView, progressBar1);
    secondTimer = new Timer(playImageViewSecondTimer, pauseImageViewSecondTimer,
            stopImageViewSecondTimer, timerTwoTextView, progressBar2);
    thirdTimer = new Timer(playImageViewThirdTimer, pauseImageViewSecondTimer,
            stopImageViewSecondTimer, timerThreeTextView, progressBar3);
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
        firstTimer.startTimer(presenter.getCountDownTime(), this);
        displayNotificationMessage("Timer - odliczanie", 1);
      }else{
        firstTimer.restartTimer(this);
      }
    });
    pauseImageViewFirstTimer.setOnClickListener(view -> firstTimer.pauseTimer());
    stopImageViewFirstTimer.setOnClickListener(view -> {
      firstTimer.stopTimer();
      cancelNotificationMessage(1);
    });
  }

  @Override
  public void setSecondTimerActions() {
    playImageViewSecondTimer.setOnClickListener(view -> {
      if(pauseImageViewSecondTimer.isEnabled()&& stopImageViewSecondTimer.isEnabled()){
        secondTimer.startTimer(presenter.getCountDownTime(), this);
        displayNotificationMessage("Timer - odliczanie", 2);
      }else{
        secondTimer.restartTimer(this);
      }
    });
    pauseImageViewSecondTimer.setOnClickListener(view -> secondTimer.pauseTimer());
    stopImageViewSecondTimer.setOnClickListener(view -> {
      secondTimer.stopTimer();
      cancelNotificationMessage(2);
    });
  }

  @Override
  public void setThirdTimerActions() {
    playImageViewThirdTimer.setOnClickListener(view -> {
      if(pauseImageViewThirdTimer.isEnabled()&& stopImageViewThirdTimer.isEnabled()){
        thirdTimer.startTimer(presenter.getCountDownTime(), this);
        displayNotificationMessage("Timer - odliczanie", 3);
      }else{
        thirdTimer.restartTimer(this);
      }
    });
    pauseImageViewThirdTimer.setOnClickListener(view -> thirdTimer.pauseTimer());
    stopImageViewThirdTimer.setOnClickListener(view -> {
      thirdTimer.stopTimer();
      cancelNotificationMessage(3);
    });
  }

  private void displayNotificationMessage(String message, int id) {
    createChannel();

    notificationBuilder
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Przepisy domowe Helasia")
            .setContentText(message);

    Intent notificationIntent = new Intent(this, TimerActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
    notificationBuilder.setContentIntent(contentIntent);

    notificationManager.notify(id, notificationBuilder.build());
  }

  private void createChannel(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      String chanelId = "1";
      CharSequence name = "Timer";
      String description = "Powiadomienia o starcie i końcu odliczania";
      int importance = NotificationManager.IMPORTANCE_LOW;
      NotificationChannel mChannel = new NotificationChannel(chanelId, name, importance);
      mChannel.setDescription(description);
      mChannel.enableLights(true);
      mChannel.setLightColor(Color.BLUE);
      notificationManager.createNotificationChannel(mChannel);
      notificationBuilder = new NotificationCompat.Builder(this, chanelId);
    } else {
      notificationBuilder = new NotificationCompat.Builder(this);
    }
  }

  private void cancelNotificationMessage(int id){
    notificationManager.cancel(id);
  }
}
