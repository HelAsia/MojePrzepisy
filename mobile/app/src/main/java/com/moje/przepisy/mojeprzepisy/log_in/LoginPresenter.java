package com.moje.przepisy.mojeprzepisy.log_in;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import com.moje.przepisy.mojeprzepisy.R;
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
  public void validateCredentials() {
    if(loginView != null) {
      userRepository.login(getLogin(), getPassword(), this);
    }
  }

  @Override
  public void onDestroy() {
    loginView = null;
  }

  @Override
  public void onLoginAndPasswordError() {
    if(loginView != null) {
      loginView.getErrorMessageTextView().setVisibility(View.VISIBLE);
      loginView.getErrorMessageTextView().setText(
          loginView.getContext().getResources()
              .getString(R.string.login_password_error_message));
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

  @Override
  public String getLogin() {
    return loginView.getLoginEditText().getText().toString();
  }

  @Override
  public String getPassword() {
    return loginView.getPasswordEditText().getText().toString();
  }
}
