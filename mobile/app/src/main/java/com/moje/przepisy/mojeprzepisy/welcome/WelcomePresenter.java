package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.welcome.WelcomeRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import okhttp3.WebSocketListener;

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
  public void onError() {
    welcomeView.getErrorTextView().setVisibility(View.VISIBLE);
    welcomeView.getErrorTextView().setText(R.string.server_connection_error);
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
    SharedPreferences.Editor userEditor = PreferenceManager.getDefaultSharedPreferences(welcomeView.getContext()).edit();
    userEditor.remove(Constant.PREF_USER_ID);
    userEditor.apply();
  }
}
