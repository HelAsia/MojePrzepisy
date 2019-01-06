package com.moje.przepisy.mojeprzepisy.setting;

import android.content.Context;

public interface SettingsContract {

    interface View{
        void setToolbar();
        Context getContext();
        void setNotificationSettings(boolean isChecked);
    }

    interface Presenter{
        void setFirstScreen();
        void changeNotificationSettings(boolean isChecked);
        void saveNotificationStateInPreferences(boolean isChecked);
    }
}
