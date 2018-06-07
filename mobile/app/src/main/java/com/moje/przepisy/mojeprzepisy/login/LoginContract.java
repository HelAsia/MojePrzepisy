package com.moje.przepisy.mojeprzepisy.login;

import android.widget.CheckBox;
import com.moje.przepisy.mojeprzepisy.BasePresenter;
import com.moje.przepisy.mojeprzepisy.BaseView;

public interface LoginContract {

  interface View extends BaseView<Presenter> {
    void setLoginButton();
    String getLogin();
    String getPassword();
    boolean getCheckboxValue(CheckBox rememberedPassword);
  }

  interface Presenter extends BasePresenter {

    void setLoginData(String login, String password, boolean checkbox);

  }
}
