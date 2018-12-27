package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Context;
import android.widget.TextView;

public interface WelcomeContract {

  interface View {
    void navigateToMainRegisteredActivity();
    void navigateToHomePage();
    void setErrorTextView(String errorMessage);
    Context getContext();
  }

  interface Presenter {
    void validateCredentialsBeforeMainMenu();
  }
}
