package com.moje.przepisy.mojeprzepisy.data.repositories.welcome;

public interface WelcomeRepositoryInterface {

  interface OnLoggedListener {

    void onLogged();

    void onNotLogged();

    void onError();
  }
  void checkUser(OnLoggedListener loggedListener);

}
