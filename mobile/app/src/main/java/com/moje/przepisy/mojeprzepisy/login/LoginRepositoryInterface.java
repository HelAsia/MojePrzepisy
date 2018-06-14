package com.moje.przepisy.mojeprzepisy.login;

public interface LoginRepositoryInterface {

  interface OnLoginFinishedListener {
    void onLoginError();

    void onPasswordError();

    void onLoginAndPasswordError();

    void onSuccess();
  }

  void login(String login, String password, OnLoginFinishedListener listener);

  boolean checkIfLoginExist(String login);

  boolean checkIfPasswordIsCorrect(String login, String password);

}
