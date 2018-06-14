package com.moje.przepisy.mojeprzepisy.login;

public class LoginPresenter implements LoginContract.Presenter, LoginRepository.OnLoginFinishedListener {

  private LoginRepository loginRepository;
  private LoginContract.View loginView;

  public LoginPresenter(LoginContract.View loginView, LoginRepository loginRepository) {
    this.loginView = loginView;
    this.loginRepository = loginRepository;
  }

  @Override
  public void validateCredentials(final String login, String password) {
    if(loginView != null) {
    }
    loginRepository.login(login,password,this );
  }

  @Override
  public void onDestroy() {
    loginView = null;
  }

  @Override
  public void onLoginAndPasswordError() {
    if(loginView != null) {
      loginView.showLoginAndPasswordError();
    }
  }

  @Override
  public void onSuccess() {
    if(loginView != null) {
      loginView.navigateToMainRegisteredActivity();
    }
  }
}
