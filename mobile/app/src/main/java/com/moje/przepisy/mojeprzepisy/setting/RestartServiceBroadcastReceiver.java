package com.moje.przepisy.mojeprzepisy.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartServiceBroadcastReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RestartServiceBroadcastReceiver.class.getSimpleName(), "Service Stops!!!");
        context.startService(new Intent(context, NewRecipeService.class));
    }
}
