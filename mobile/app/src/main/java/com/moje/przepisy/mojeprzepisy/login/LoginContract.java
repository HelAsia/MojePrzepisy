package com.moje.przepisy.mojeprzepisy.login;

public interface LoginContract {

  interface View {

    void navigateToMainRegisteredActivity();

    String getLogin();

    String getPassword();

    void showLoginError();

    void showPasswordError();

    void showLoginAndPasswordError();

    void showProgress();

    void hideProgress();
  }

  interface Presenter {

    void validateCredentials(String login, String password);

    void onDestroy();
  }
}
