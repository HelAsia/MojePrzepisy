package com.moje.przepisy.mojeprzepisy.welcome;

public interface WelcomeRepositoryInterface {

  interface OnLoggedListener {

    void onLogged();

    void onNotLogged();
  }
  void checkUser(OnLoggedListener loggedListener);

}
