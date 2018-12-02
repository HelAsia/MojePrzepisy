package com.moje.przepisy.mojeprzepisy.log_out;

import android.widget.TextView;

public interface LogoutContract {
  interface View {
    void navigateToMainLoggedCardsActivity();
    void navigateToUnLoggedMainCardsActivity();
    TextView getErrorMessageTextView();
  }

  interface Presenter {
    void validateCredentials();
    void onDestroy();
    void onCancel();
  }
}
