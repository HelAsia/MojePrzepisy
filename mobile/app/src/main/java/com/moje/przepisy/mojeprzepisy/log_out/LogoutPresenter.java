package com.moje.przepisy.mojeprzepisy.log_out;

import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user.UserRepository;

public class LogoutPresenter implements LogoutContract.Presenter, UserRepository.OnLogoutFinishedListener {

  private UserRepository userRepository;
  private LogoutContract.View logoutView;

  public LogoutPresenter(LogoutContract.View logoutView, UserRepository userRepository){
    this.logoutView = logoutView;
    this.userRepository = userRepository;
  }

  @Override
  public void validateCredentials() {
    if(logoutView != null) {
      userRepository.logout(this);
    }
  }

  @Override
  public void onDestroy() {
    logoutView = null;
  }

  @Override
  public void onLogoutError(String message) {
    if(logoutView != null) {
      logoutView.showLogoutError(message);
    }
  }

  @Override
  public void onSuccess() {
    if(logoutView != null) {
      logoutView.navigateToUnloggedMainCardsActivity();
    }
  }
}
