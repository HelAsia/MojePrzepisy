package com.moje.przepisy.mojeprzepisy.utils;

import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

public class Timer {
    private CountDownTimer countDownTimer;
    private boolean isPausedTimer = false;
    private boolean isCanceledTimer = false;
    private long timeRemainingTimer = 0;
    private ImageView playImage;
    private ImageView pauseImage;
    private ImageView stopImage;
    private TextView timerText;

    public Timer(ImageView playImage, ImageView pauseImage, ImageView stopImage, TextView timerText){
        this.playImage = playImage;
        this.pauseImage = pauseImage;
        this.stopImage = stopImage;
        this.timerText = timerText;
    }

    private String formatNumber(long value){
        if(value < 10)
            return "0" + value;
        return value + "";
    }

    public void startTimer(int countDownTime) {
        isPausedTimer = false;
        isCanceledTimer = false;
        playImage.setEnabled(false);
        pauseImage.setEnabled(true);
        stopImage.setEnabled(true);

        countDownTimer = new CountDownTimer(countDownTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished/1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                if(isPausedTimer || isCanceledTimer){
                    cancel();
                }else {
                    if(minutes > 0)
                        seconds = seconds % 60;
                    if(hours > 0)
                        minutes = minutes % 60;
                    String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
                            formatNumber(seconds);
                    timerText.setText(time);

                    timeRemainingTimer = millisUntilFinished;
                }
            }

            @Override
            public void onFinish() {
                timerText.setText("DONE");
                playImage.setEnabled(true);
                pauseImage.setEnabled(false);
                stopImage.setEnabled(false);
            }
        }.start();
    }

    public void pauseTimer() {
        isPausedTimer = true;

        playImage.setEnabled(true);
        pauseImage.setEnabled(false);
        stopImage.setEnabled(false);
    }

    public void restartTimer() {
        isPausedTimer = false;
        isCanceledTimer = false;

        playImage.setEnabled(false);
        pauseImage.setEnabled(true);
        playImage.setEnabled(true);

        countDownTimer = new CountDownTimer(timeRemainingTimer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished/1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                if(isPausedTimer || isCanceledTimer){
                    cancel();
                }else {
                    if(minutes > 0)
                        seconds = seconds % 60;
                    if(hours > 0)
                        minutes = minutes % 60;
                    String time = formatNumber(hours) + ":" + formatNumber(minutes) + ":" +
                            formatNumber(seconds);
                    timerText.setText(time);

                    timeRemainingTimer = millisUntilFinished;
                }
            }

            @Override
            public void onFinish() {
                timerText.setText("DONE");
                playImage.setEnabled(true);
                pauseImage.setEnabled(false);
                stopImage.setEnabled(false);
            }
        }.start();
    }

    public void stopTimer() {
        isCanceledTimer = true;

        playImage.setEnabled(true);
        pauseImage.setEnabled(false);
        stopImage.setEnabled(false);

        timerText.setText("00:00:00");
    }
}
