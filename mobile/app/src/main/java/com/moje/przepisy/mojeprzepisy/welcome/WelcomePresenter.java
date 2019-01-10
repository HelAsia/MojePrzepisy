package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.welcome.WelcomeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class WelcomePresenter implements WelcomeContract.Presenter, WelcomeRepository.OnLoggedListener {
  private WelcomeRepository welcomeRepository;
  private WelcomeContract.View welcomeView;

  WelcomePresenter(WelcomeContract.View welcomeView, WelcomeRepository welcomeRepository) {
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
  public void onError() {
    String errorMessage = welcomeView.getContext().getResources()
            .getString(R.string.server_connection_error);
    welcomeView.setErrorTextView(errorMessage);
  }


  @Override
  public void onLogged() {
    if(welcomeView != null){
      welcomeView.navigateToMainRegisteredActivity();
      welcomeView.setNotificationSettings(getNotificationSetting());
    }
  }

  @Override
  public void onNotLogged() {
    if(welcomeView != null){
      deleteUserIdFromPreferences();
      welcomeView.navigateToHomePage();
    }
  }

  private void deleteUserIdFromPreferences(){
    SharedPreferences.Editor userEditor = PreferenceManager.getDefaultSharedPreferences(welcomeView.getContext()).edit();
    userEditor.remove(Constant.PREF_USER_ID);
    userEditor.apply();
  }

  private boolean getNotificationSetting(){
    return PreferenceManager.getDefaultSharedPreferences(welcomeView.getContext())
            .getBoolean(Constant.PREF_NOTIFICATION, false);
  }
}
