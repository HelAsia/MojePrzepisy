package com.moje.przepisy.mojeprzepisy.welcome;

public class WelcomePresenter implements WelcomeContract.Presenter, WelcomeRepository {

  @Override
  public boolean validateCredentialsBeforeMainMenu(String userID) {
    return false;
  }

  @Override
  public void onDestroy() {

  }
}
