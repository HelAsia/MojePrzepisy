package com.moje.przepisy.mojeprzepisy.timer;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

class Timer {
    private boolean isPausedTimer = false;
    private boolean isCanceledTimer = false;
    private long timeRemainingTimer = 0;
    private int countDownTime = 0;
    private ImageView playImage;
    private ImageView pauseImage;
    private ImageView stopImage;
    private TextView timerText;
    private RingProgressBar progressBar;

    Timer(ImageView playImage, ImageView pauseImage, ImageView stopImage, TextView timerText,
          RingProgressBar progressBar){
        this.playImage = playImage;
        this.pauseImage = pauseImage;
        this.stopImage = stopImage;
        this.timerText = timerText;
        this.progressBar = progressBar;
    }

    private String formatNumber(long value){
        if(value < 10)
            return "0" + value;
        return value + "";
    }

    private void turnOnSound(Context context){
        Uri notificationSoundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager
                .getRingtone(context, notificationSoundUri);
        ringtone.play();
    }

    private void turnOnVibrate(Context context){
        ((Vibrator)context.getSystemService(
                Context.VIBRATOR_SERVICE)).vibrate(1000);
    }

    private void setCountDownTimerText(long millisUntilFinished){
        long seconds = millisUntilFinished/1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if(minutes > 0)
            seconds = seconds % 60;
        if(hours > 0)
            minutes = minutes % 60;
        String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
                formatNumber(seconds);
        timerText.setText(time);

        int progress = ((int)millisUntilFinished * 100)/countDownTime;
        progressBar.setProgress(progress);

        timeRemainingTimer = millisUntilFinished;
    }

    void startTimer(int countDownTime, Context context) {
        isPausedTimer = false;
        isCanceledTimer = false;
        playImage.setEnabled(false);
        pauseImage.setEnabled(true);
        stopImage.setEnabled(true);
        this.countDownTime = countDownTime;

        new CountDownTimer(countDownTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPausedTimer || isCanceledTimer){
                    cancel();
                }else {
                    setCountDownTimerText(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                turnOnSound(context);
                turnOnVibrate(context);
                progressBar.setProgress(0);
                timerText.setText("00:00:00");
                playImage.setEnabled(true);
                pauseImage.setEnabled(false);
                stopImage.setEnabled(false);
            }
        }.start();
    }

    void pauseTimer() {
        isPausedTimer = true;

        playImage.setEnabled(true);
        pauseImage.setEnabled(false);
        stopImage.setEnabled(false);
    }

    void restartTimer(Context context) {
        isPausedTimer = false;
        isCanceledTimer = false;

        playImage.setEnabled(false);
        pauseImage.setEnabled(true);
        playImage.setEnabled(true);

        new CountDownTimer(timeRemainingTimer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPausedTimer || isCanceledTimer){
                    cancel();
                }else {
                    setCountDownTimerText(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                turnOnSound(context);
                turnOnVibrate(context);
                progressBar.setProgress(0);
                timerText.setText("00:00:00");
                playImage.setEnabled(true);
                pauseImage.setEnabled(false);
                stopImage.setEnabled(false);
            }
        }.start();
    }

    void stopTimer() {
        isCanceledTimer = true;

        playImage.setEnabled(true);
        pauseImage.setEnabled(false);
        stopImage.setEnabled(false);
        progressBar.setProgress(0);
        timerText.setText("00:00:00");
    }
}
