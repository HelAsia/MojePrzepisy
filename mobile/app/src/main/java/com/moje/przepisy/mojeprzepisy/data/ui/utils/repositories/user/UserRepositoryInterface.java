package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user;

public interface UserRepositoryInterface {

  interface OnLoginFinishedListener {

    void onLoginAndPasswordError();

    void onSuccess();
  }
  void login(String login, String password, OnLoginFinishedListener listener);

  interface OnLogoutFinishedListener {

    void onLogoutError(String message);

    void onSuccess();
  }
  void logout(OnLogoutFinishedListener listener);

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
