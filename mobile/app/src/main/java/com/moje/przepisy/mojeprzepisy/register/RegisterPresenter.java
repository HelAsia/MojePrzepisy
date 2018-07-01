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

  }

  @Override
  public void onDestroy() {
    registerView = null;
  }


  @Override
  public void onLoginError() {

  }

  @Override
  public void onPasswordOrEmailError() {

  }

  @Override
  public void onSuccess() {

  }
}
