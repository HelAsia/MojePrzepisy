package com.moje.przepisy.mojeprzepisy.logOut;

public interface LogOutContract {
  interface View {
    void navigateToMainLoggedCardsActivity();
    void navigateToUnLoggedMainCardsActivity();
    void setErrorMessage(String error);
  }

  interface Presenter {
    void validateCredentials();
    void onCancel();
  }
}
