package com.moje.przepisy.mojeprzepisy.register;

import android.content.Context;

public interface RegisterContract {
  interface View {
    void navigateToMainRegisteredActivity();
    String getName();
    String getLastName();
    String getLogin();
    String getPassword();
    String getRepeatedPassword();
    String getEmail();
    String getRepeatedEmail();
    Context getContext();
    void setErrorTextView(String errorMessage);
  }

  interface Presenter {
    void validateCredentials();
  }
}
