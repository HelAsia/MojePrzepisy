package com.moje.przepisy.mojeprzepisy.register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterRepository.OnRegisterFinishedListener {

  private RegisterRepository registerRepository;
  private RegisterContract.View registerView;

  public RegisterPresenter(RegisterContract.View registerView, RegisterRepository registerRepository) {
    this.registerView = registerView;
    this.registerRepository = registerRepository;
  }

  @Override
  public void validateCredentials(String name, String lastName, String login, String password,
      String email) {
    if(registerView != null) {
      registerRepository.register(name, lastName, login, password, email,this);
    }
  }

  @Override
  public void onDestroy() {
    registerView = null;
  }

  @Override
  public void onLoginError() {
    if(registerView != null) {
      registerView.showLoginError();
    }
  }

  @Override
  public boolean onPasswordOrEmailError() {
    if(registerView != null) {
      if(!registerView.getPassword().equals(registerView.getRepeatedPassword())){
        registerView.showPasswordError();
        return false;
      }else if(!registerView.getEmail().equals(registerView.getRepeatedEmail())){
        registerView.showEmailError();
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean onValidatePasswordError(String password) {
    if(registerView != null){
      String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])(?=.*[A-Z])(?!.*\\s).{8,}$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(password);

      if(!matcher.matches()){
        registerView.showValidatePasswordError();
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean onValidateEmailError(String email) {
    if(registerView != null){
      String regex = "^[-\\w\\.]+@([-\\w]+\\.)+[a-z]+$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(email);

      if(!matcher.matches()){
        registerView.showValidateEmailError();
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onSuccess() {
    if(registerView != null) {
      registerView.navigateToMainRegisteredActivity();
    }
  }

  @Override
  public void onOtherError(String message) {
    if(registerView != null) {
      registerView.showOtherError(message);
    }
  }
}
