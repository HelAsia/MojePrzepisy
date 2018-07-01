package com.moje.przepisy.mojeprzepisy.register;

public interface RegisterContract {

  interface View {

    void navigateToMainRegisteredActivity();

    String getName();

    String getLastName();

    String getLogin();

    String getPassword();

    String getRepeatedPassword();

    String getEmail();

    String getRepeatedEmail();

    void showLoginError();

    void showPasswordOrEmailError();
  }

  interface Presenter {

    void validateCredentials(String name, String lastName, String login, String password, String email);

    void onDestroy();
  }

}
