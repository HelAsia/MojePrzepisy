package com.moje.przepisy.mojeprzepisy.register;

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
  public void onPasswordOrEmailError() {
    if(registerView != null) {
      if(!registerView.getPassword().equals(registerView.getRepeatedPassword())){
        registerView.showPasswordError();
      }else if(!registerView.getEmail().equals(registerView.getRepeatedEmail())){
        registerView.showEmailError();
      }
    }
  }

  @Override
  public void onSuccess() {
    if(registerView != null) {
      registerView.navigateToMainRegisteredActivity();
    }
  }
}
