package com.moje.przepisy.mojeprzepisy.aboutApplication;

public class AboutApplicationPresenter implements AboutApplicationContract.Presenter {
  private AboutApplicationContract.View aboutApplicationView;

  AboutApplicationPresenter(AboutApplicationContract.View aboutApplicationView){
    this.aboutApplicationView = aboutApplicationView;
  }

  @Override
  public void setFirstScreen() {
    aboutApplicationView.setLogged();
    aboutApplicationView.setToolbar();
  }
}
