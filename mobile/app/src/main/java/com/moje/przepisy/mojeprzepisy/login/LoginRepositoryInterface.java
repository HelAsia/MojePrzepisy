package com.moje.przepisy.mojeprzepisy.login;

public interface LoginRepositoryInterface {

  boolean wrongLoginOrPassword(String login, String password);

  void saveData (String login, String password, boolean checkbox);

  String sendMessageAfterSaved();


}
