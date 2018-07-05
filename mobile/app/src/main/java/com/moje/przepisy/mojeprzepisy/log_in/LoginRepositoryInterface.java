package com.moje.przepisy.mojeprzepisy.log_in;

public interface LoginRepositoryInterface {

  interface OnLoginFinishedListener {

    void onLoginAndPasswordError();

    void onSuccess();
  }

  void login(String login, String password, OnLoginFinishedListener listener);

}
