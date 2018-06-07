package com.moje.przepisy.mojeprzepisy.login;

public class LoginPresenter implements LoginContract.Presenter {

  private final LoginRepository loginRepository;

  private final LoginContract.View loginView;

  String login;

  String password;

  boolean checkbox;


  @Override
  public void start() {

  }

  @Override
  public void setLoginData(String login, String password, boolean checkbox) {

    login.this = login;
    password.this = password;
    checkbox.this = checkbox;

  }
}
