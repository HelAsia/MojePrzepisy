package com.moje.przepisy.mojeprzepisy.log_out;

public interface LogoutContract {

  interface View {

    void navigateToMainCardsActivity();

    void showLogoutError(String message);
  }

  interface Presenter {

    void validateCredentials();

    void onDestroy();
  }
}
