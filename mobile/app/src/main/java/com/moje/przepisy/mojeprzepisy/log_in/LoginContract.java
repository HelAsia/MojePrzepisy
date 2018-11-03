package com.moje.przepisy.mojeprzepisy.log_in;

import android.content.Context;

public interface LoginContract {
  interface View {
    void navigateToMainRegisteredActivity();
    String getLogin();
    String getPassword();
    void showLoginAndPasswordError();
    Context getContext();
  }

  interface Presenter {
    void validateCredentials(String login, String password);
    void onDestroy();
  }
}
