package com.moje.przepisy.mojeprzepisy.logOut;

import android.view.View;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;

public class LogOutPresenter implements LogOutContract.Presenter,
    UserRepository.OnLogoutFinishedListener {

  private UserRepository userRepository;
  private LogOutContract.View logoutView;

  public LogOutPresenter(LogOutContract.View logoutView, UserRepository userRepository){
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
  public void onCancel() {
    logoutView.navigateToMainLoggedCardsActivity();
  }

  @Override
  public void onLogoutError(String message) {
    if(logoutView != null) {
      logoutView.setErrorMessage(message);
    }
  }

  @Override
  public void onSuccess() {
    if(logoutView != null) {
      logoutView.navigateToUnLoggedMainCardsActivity();
    }
  }
}
