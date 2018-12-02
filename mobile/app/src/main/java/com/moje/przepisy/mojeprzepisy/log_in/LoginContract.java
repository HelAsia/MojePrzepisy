package com.moje.przepisy.mojeprzepisy.log_in;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public interface LoginContract {
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
