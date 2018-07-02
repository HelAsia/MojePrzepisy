package com.moje.przepisy.mojeprzepisy.register;

public interface RegisterRepositoryInterface {

  interface OnRegisterFinishedListener {

    void onLoginError();

    void onPasswordOrEmailError();

    void onSuccess();
  }

  void register(String name, String lastName, String login, String password, String email, OnRegisterFinishedListener listener);
}
