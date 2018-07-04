package com.moje.przepisy.mojeprzepisy.register;

public interface RegisterRepositoryInterface {

  interface OnRegisterFinishedListener {

    void onLoginError();

    boolean onPasswordOrEmailError();

    boolean onValidatePasswordError(String password);

    boolean onValidateEmailError(String email);

    void onSuccess();

    void onOtherError(String message);
  }

  void register(String name, String lastName, String login, String password, String email, OnRegisterFinishedListener listener);
}
