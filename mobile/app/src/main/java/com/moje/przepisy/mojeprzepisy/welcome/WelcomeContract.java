package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Context;
import android.widget.TextView;

public interface WelcomeContract {

  interface View {
    void navigateToMainRegisteredActivity();
    void navigateToHomePage();
    TextView getErrorTextView();
    Context getContext();
  }

  interface Presenter {
    void validateCredentialsBeforeMainMenu();
    void onDestroy();
  }
}
