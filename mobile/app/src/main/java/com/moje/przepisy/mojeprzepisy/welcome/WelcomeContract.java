package com.moje.przepisy.mojeprzepisy.welcome;

public interface WelcomeContract {

  interface View {

    void navigateToMainRegisteredActivity();
    void navigateToHomePage();

  }

  interface Presenter {

    boolean validateCredentialsBeforeMainMenu(String userID);

    void onDestroy();
  }
}
