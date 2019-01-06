package com.moje.przepisy.mojeprzepisy.setting;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class SettingsPresenter implements SettingsContract.Presenter{
    private SettingsContract.View settingsView;

    SettingsPresenter(SettingsContract.View settingsView) {
        this.settingsView = settingsView;
    }

    @Override
    public void setFirstScreen() {
        boolean preferencesState = PreferenceManager.getDefaultSharedPreferences(settingsView.getContext())
                .getBoolean(Constant.PREF_NOTIFICATION, false);
        settingsView.setToolbar();
        settingsView.setNotificationSettings(preferencesState);
    }

    @Override
    public void changeNotificationSettings(boolean isChecked) {
        settingsView.setNotificationSettings(isChecked);
        saveNotificationStateInPreferences(isChecked);
    }

    @Override
    public void saveNotificationStateInPreferences(boolean isChecked) {
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(settingsView.getContext()).edit();
        preferences.putBoolean(Constant.PREF_NOTIFICATION, isChecked).apply();
        preferences.commit();
    }
}
