package com.moje.przepisy.mojeprzepisy.setting;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Switch;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements SettingsContract.View {
    @BindView(R.id.toolbar_settings) Toolbar toolbar;
    @BindView(R.id.notification_on_off_switch) Switch notificationSwitch;
    private SettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        settingsPresenter = new SettingsPresenter(this);

        settingsPresenter.setFirstScreen();
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                settingsPresenter.changeNotificationSettings(isChecked));
    }

    @Override
    public void setToolbar() {
        toolbar.setSubtitle(R.string.title_activity_settings);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setNotificationSettings(boolean isChecked) {
        if(isChecked){
            notificationSwitch.setChecked(true);
            notificationSwitch.setText(this.getResources().getString(R.string.notification_settings_on));
            NewRecipeService newRecipeService = new NewRecipeService();
            Intent serviceIntent = new Intent(this, newRecipeService.getClass());
            if (!isMyServiceRunning(newRecipeService.getClass())) {
                startService(serviceIntent);
            }
        }else{
            notificationSwitch.setChecked(false);
            notificationSwitch.setText(this.getResources().getString(R.string.notification_settings_off));
            stopService(new Intent(this, NewRecipeService.class));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this, MainCardsActivity.class);
        intent.putExtra("isLogged", true);
        startActivity(intent);
        SettingsActivity.this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainCardsActivity.class);
        intent.putExtra("isLogged", true);
        startActivity(intent);
        SettingsActivity.this.finish();
    }
}
