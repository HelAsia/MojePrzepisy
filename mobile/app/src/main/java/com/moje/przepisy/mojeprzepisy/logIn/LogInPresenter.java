package com.moje.przepisy.mojeprzepisy.logIn;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class LogInPresenter implements LogInContract.Presenter,
        UserRepository.OnLoginFinishedListener {
  private UserRepository userRepository;
  private LogInContract.View loginView;

  public LogInPresenter(LogInContract.View loginView, UserRepository userRepository) {
    this.loginView = loginView;
    this.userRepository = userRepository;
  }

  @Override
  public void validateCredentials() {
    if(loginView != null) {
      userRepository.login(loginView.getLogin(), loginView.getPassword(), this);
    }
  }

  @Override
  public void onLoginAndPasswordError() {
    if(loginView != null) {
      String errorMessage = loginView.getContext().getResources()
              .getString(R.string.login_password_error_message);
      loginView.setErrorMessageTextView(errorMessage);

    }
  }

  @Override
  public void onSuccess(int userId) {
    if(loginView != null) {
      saveUserIdInPreferences(userId);
      loginView.navigateToMainRegisteredActivity();
    }
  }

  private void saveUserIdInPreferences(int userId){
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(loginView.getContext()).edit();
    editor.putInt(Constant.PREF_USER_ID, userId).apply();
    editor.commit();
  }
}
