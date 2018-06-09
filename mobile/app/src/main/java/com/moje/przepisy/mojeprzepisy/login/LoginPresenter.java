package com.moje.przepisy.mojeprzepisy.login;

import com.moje.przepisy.mojeprzepisy.data.UsersRepository;

public class LoginPresenter implements LoginContract.Presenter, UsersRepository.OnLoginFinishedListener {

  private UsersRepository loginRepository;
  private LoginContract.View loginView;

  public LoginPresenter(LoginContract.View loginView, UsersRepository loginRepository) {
    this.loginView = loginView;
    this.loginRepository = loginRepository;
  }

  @Override
  public void validateCredentials(String login, String password) {

    if(loginView != null) {
      loginView.showProgress();
    }
    loginRepository.login(login,password,this );
  }

  @Override
  public void onDestroy() {
    loginView = null;
  }

  @Override
  public void onLoginError() {
    if (loginView != null) {
      loginView.showLoginError();
      loginView.hideProgress();
    }
  }

  @Override
  public void onPasswordError() {
    if (loginView != null) {
      loginView.showPasswordError();
      loginView.hideProgress();
    }
  }

  @Override
  public void onLoginAndPasswordError() {
    if(loginView != null) {
      loginView.showLoginAndPasswordError();
      loginView.hideProgress();
    }
  }

  @Override
  public void onSuccess() {
    if(loginView != null) {
      loginView.navigateToMainRegisteredActivity();
    }
  }
}
