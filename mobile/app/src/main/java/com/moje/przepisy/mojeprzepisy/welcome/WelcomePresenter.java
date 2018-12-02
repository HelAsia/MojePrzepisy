package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.welcome.WelcomeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

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
      deleteUserIdFromPreferences();
      welcomeView.navigateToHomePage();
    }
  }

  public void deleteUserIdFromPreferences(){
    SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(welcomeView.getContext()).edit();
    ingredientsEditor.remove(Constant.PREF_USER_ID);
    ingredientsEditor.apply();
  }
}
