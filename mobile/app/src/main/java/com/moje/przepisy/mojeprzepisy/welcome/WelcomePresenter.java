package com.moje.przepisy.mojeprzepisy.welcome;

public class WelcomePresenter implements WelcomeContract.Presenter, WelcomeRepository.OnLoggedListener {
  private WelcomeRepository welcomeRepository;
  private WelcomeContract.View welcomeView;

  public WelcomePresenter(WelcomeContract.View welcomeView, WelcomeRepository welcomeRepository) {
    this.welcomeView = welcomeView;
    this.welcomeRepository = welcomeRepository;
  }

  @Override
  public void validateCredentialsBeforeMainMenu() {
    if(welcomeView != null){
      welcomeRepository.checkUser(this);
    }
  }

  @Override
  public void showErrorMessage() {
    welcomeView.errorMessage();
  }

  @Override
  public void onDestroy() {
    welcomeView = null;
  }


  @Override
  public void onLogged() {
    if(welcomeView != null){
      welcomeView.navigateToMainRegisteredActivity();
    }
  }

  @Override
  public void onNotLogged() {
    if(welcomeView != null){
      welcomeView.navigateToHomePage();
    }
  }
}
