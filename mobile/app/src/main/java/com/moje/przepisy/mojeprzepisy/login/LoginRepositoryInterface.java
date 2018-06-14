package com.moje.przepisy.mojeprzepisy.login;

public interface LoginRepositoryInterface {

  interface OnLoginFinishedListener {

    void onLoginAndPasswordError();

    void onSuccess();
  }

  void login(String login, String password, OnLoginFinishedListener listener);

}
