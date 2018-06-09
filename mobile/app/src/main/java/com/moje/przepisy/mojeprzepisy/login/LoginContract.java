package com.moje.przepisy.mojeprzepisy.login;

import com.moje.przepisy.mojeprzepisy.BasePresenter;
import com.moje.przepisy.mojeprzepisy.BaseView;

public interface LoginContract {

  interface View extends BaseView<Presenter> {

    void navigateToMainRegisteredActivity();

    String getLogin();

    String getPassword();

    boolean getCheckboxValue();

    void showLoginError();

    void showPasswordError();

    void showLoginAndPasswordError();

    void showProgress();

    void hideProgress();
  }

  interface Presenter extends BasePresenter {

    void validateCredentials(String login, String password);

    void onDestroy();
  }
}
