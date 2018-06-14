package com.moje.przepisy.mojeprzepisy.register;

public interface RegisterRepositoryInterface {

  interface OnRegisterFinishedListener {
    void onLoginError();

    void onPasswordError();

    void onLoginAndPasswordError();

    void onSuccess();
  }
}
