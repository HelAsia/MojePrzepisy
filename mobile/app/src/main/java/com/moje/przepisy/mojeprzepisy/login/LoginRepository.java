package com.moje.przepisy.mojeprzepisy.login;

public class LoginRepository implements LoginRepositoryInterface{

  @Override
  public boolean wrongLoginOrPassword(String login, String password) {
    return false;
  }

  @Override
  public void saveData(String login, String password, boolean checkbox) {

  }

  @Override
  public String sendMessageAfterSaved() {
    return null;
  }
}
