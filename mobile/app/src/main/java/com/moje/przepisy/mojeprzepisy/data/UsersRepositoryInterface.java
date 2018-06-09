package com.moje.przepisy.mojeprzepisy.data;

public interface UsersRepositoryInterface {

  interface OnLoginFinishedListener {
    void onLoginError();

    void onPasswordError();

    void onLoginAndPasswordError();

    void onSuccess();
  }

  interface OnRegisterFinishedListener {

  }

  void login(String login, String password, OnLoginFinishedListener listener);

  boolean checkIfLoginExist(String login);

  boolean checkIfPasswordIsCorrect(String login, String password);

}
