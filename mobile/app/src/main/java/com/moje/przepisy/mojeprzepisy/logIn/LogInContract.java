package com.moje.przepisy.mojeprzepisy.logIn;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public interface LogInContract {
  interface View {
    void navigateToMainRegisteredActivity();
    TextView getErrorMessageTextView();
    EditText getLoginEditText();
    EditText getPasswordEditText();
    Context getContext();
  }

  interface Presenter {
    void validateCredentials();
    void onDestroy();
    String getLogin();
    String getPassword();
  }
}
