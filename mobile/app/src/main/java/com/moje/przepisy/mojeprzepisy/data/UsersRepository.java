package com.moje.przepisy.mojeprzepisy.data;

import android.text.TextUtils;

public class UsersRepository implements UsersRepositoryInterface {

  @Override
  public void login(final String login, final String password, final OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(login)) {
          listener.onLoginError();
        }
        if (TextUtils.isEmpty(password)) {
          listener.onPasswordError();
        }
        if(TextUtils.isEmpty(login)&&TextUtils.isEmpty(password)) {
          listener.onLoginAndPasswordError();
        }
        if(!TextUtils.isEmpty(login)&&!TextUtils.isEmpty(password)) {
          if(checkIfLoginExist(login)){
            if(checkIfPasswordIsCorrect(login, password)){
              listener.onSuccess();
            }
            listener.onPasswordError();
          }
          listener.onLoginAndPasswordError();
        }
  }

  @Override
  public boolean checkIfLoginExist(String login) {
    return true;

  }

  @Override
  public boolean checkIfPasswordIsCorrect(String login, String password) {
    return true;
  }
}
