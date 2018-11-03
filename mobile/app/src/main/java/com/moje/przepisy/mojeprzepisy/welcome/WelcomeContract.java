package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Context;

public interface WelcomeContract {

  interface View {
    void navigateToMainRegisteredActivity();
    void navigateToHomePage();
    void errorMessage();
    Context getContext();
  }

  interface Presenter {
    void validateCredentialsBeforeMainMenu();
    void onDestroy();
  }
}
