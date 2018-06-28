package com.moje.przepisy.mojeprzepisy.welcome;

public interface WelcomeContract {

  interface View {

    void navigateToMainRegisteredActivity();
    void navigateToHomePage();

  }

  interface Presenter {

    void validateCredentialsBeforeMainMenu();

    void onDestroy();
  }
}
