package com.moje.przepisy.mojeprzepisy.logIn;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public interface LogInContract {
  interface View {
    void navigateToMainRegisteredActivity();
    void setErrorMessageTextView(String errorMessage);
    String getLogin();
    String getPassword();
    Context getContext();
  }

  interface Presenter {
    void validateCredentials();
  }
}
