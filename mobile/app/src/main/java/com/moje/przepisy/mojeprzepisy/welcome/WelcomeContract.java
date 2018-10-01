package com.moje.przepisy.mojeprzepisy.welcome;

public interface WelcomeContract {

  interface View {

    void navigateToMainRegisteredActivity();
    void navigateToHomePage();
    void errorMessage();

  }

  interface Presenter {

    void validateCredentialsBeforeMainMenu();

    void onDestroy();
  }
}
