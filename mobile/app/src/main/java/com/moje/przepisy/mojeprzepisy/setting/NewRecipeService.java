package com.moje.przepisy.mojeprzepisy.setting;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.cards.OperationsOnCardRepository;
import com.moje.przepisy.mojeprzepisy.data.repositories.cards.OperationsOnCardRepositoryInterface;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class NewRecipeService extends Service implements
        OperationsOnCardRepositoryInterface.OnNewCardsListener {
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private ThreadGroup myThreads = new ThreadGroup("ServiceWorker");

    private OperationsOnCardRepository operationsOnCardRepository =
            new OperationsOnCardRepository(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        new Thread(myThreads, new ServiceWorker(true), "NewRecipeService")
                .start();

        return START_STICKY;
    }

    class ServiceWorker implements Runnable{
        boolean notificationOn;

        ServiceWorker(boolean notificationOn){
            this.notificationOn = notificationOn;
        }

        @Override
        public void run() {
            checkNewRecipe(notificationOn);
        }
    }

    private void checkNewRecipe(boolean notificationOn){
        while(notificationOn){
            int maxDate = PreferenceManager.getDefaultSharedPreferences(this)
                    .getInt(Constant.PREF_MAX_DATE, 0);
            notificationOn = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean(Constant.PREF_NOTIFICATION, false);
            operationsOnCardRepository.getNewCards(this, maxDate);
            try{
                Thread.sleep(3600000);
            }catch (InterruptedException e){
                Log.i("NewRecipeService", "InterruptedException");
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this, RestartServiceBroadcastReceiver.class);

        sendBroadcast(broadcastIntent);

        myThreads.interrupt();
        if(notificationManager != null){
            notificationManager.cancelAll();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setNewCardsNotification() {
        notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationBuilderWithChannel();
        setNotificationBuilder();

        StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        if(notifications.length != 0){
            for (StatusBarNotification notification : notifications) {
                if (notification.getId() != 0) {
                    notificationManager.notify(0, notificationBuilder.build());
                    turnOnSound();
                    turnOnVibrate();
                }
            }
        }
        else{
            notificationManager.notify(0, notificationBuilder.build());
            turnOnSound();
            turnOnVibrate();
        }

    }

    private void createNotificationBuilderWithChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanelId = "3000";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
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

    private void setNotificationBuilder(){
        notificationBuilder
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Przepisy domowe Helasia")
                .setContentText("Sprawdź nowe przepisy!")
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(this, MainCardsActivity.class);
        notificationIntent.putExtra("isLogged", true);
        setSortedMethod();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
    }

    private void turnOnSound(){
        Uri notificationSoundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager
                .getRingtone(this, notificationSoundUri);
        ringtone.play();
    }

    private void turnOnVibrate(){
        ((Vibrator)this.getSystemService(
                Context.VIBRATOR_SERVICE)).vibrate(1000);
    }

    private void setSortedMethod(){
        SharedPreferences.Editor sortingSetting = PreferenceManager.getDefaultSharedPreferences(this).edit();
        sortingSetting.putString(Constant.PREF_SORTED_METHOD, "lastAdded").apply();
        sortingSetting.commit();
    }
}
