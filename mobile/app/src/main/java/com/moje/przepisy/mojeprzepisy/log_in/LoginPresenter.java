package com.moje.przepisy.mojeprzepisy.log_in;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class LoginPresenter implements LoginContract.Presenter, UserRepository.OnLoginFinishedListener {

  private UserRepository userRepository;
  private LoginContract.View loginView;

  public LoginPresenter(LoginContract.View loginView, UserRepository userRepository) {
    this.loginView = loginView;
    this.userRepository = userRepository;
  }

  @Override
  public void validateCredentials(final String login, String password) {
    if(loginView != null) {
      userRepository.login(login, password, this);
    }
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
  public void onSuccess(int userId) {
    if(loginView != null) {
      saveUserIdInPreferences(userId);
      loginView.navigateToMainRegisteredActivity();
    }
  }

  public void saveUserIdInPreferences(int userId){
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(loginView.getContext()).edit();
    editor.putInt(Constant.PREF_USER_ID, userId).apply();
    editor.commit();
  }
}
