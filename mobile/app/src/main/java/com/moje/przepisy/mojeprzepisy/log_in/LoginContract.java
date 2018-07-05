package com.moje.przepisy.mojeprzepisy.log_in;

public interface LoginContract {

  interface View {

    void navigateToMainRegisteredActivity();

    String getLogin();

    String getPassword();

    void showLoginAndPasswordError();
  }

  interface Presenter {

    void validateCredentials(String login, String password);

    void onDestroy();
  }
}
