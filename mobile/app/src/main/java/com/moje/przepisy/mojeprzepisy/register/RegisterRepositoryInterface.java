package com.moje.przepisy.mojeprzepisy.register;

import com.moje.przepisy.mojeprzepisy.login.LoginRepositoryInterface.OnLoginFinishedListener;

public interface RegisterRepositoryInterface {

  interface OnRegisterFinishedListener {

    void onLoginError();

    void onPasswordOrEmailError();

    void onSuccess();
  }

  void register(String name, String lastName, String login, String password, String email, OnLoginFinishedListener listener);
}
