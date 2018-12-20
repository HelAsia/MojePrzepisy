package com.moje.przepisy.mojeprzepisy.aboutApplication;

public class AboutApplicationPresenter implements AboutApplicationContract.Presenter {
  private AboutApplicationContract.View aboutApplicationView;
  private Boolean isDescriptionInfoOpen = false;
  private Boolean isAuthorInfoOpen = false;
  private Boolean isVersionInfoOpen = false;
  private Boolean isErrorInfoOpen = false;

  public AboutApplicationPresenter(AboutApplicationContract.View aboutApplicationView){
    this.aboutApplicationView = aboutApplicationView;
  }

  @Override
  public void setFirstScreen() {
    aboutApplicationView.setToolbar();
    aboutApplicationView.setDownArrow();
    aboutApplicationView.setRightArrow();
    aboutApplicationView.setDetailsOnClickListeners();
  }

  @Override
  public void openOrCloseAppDescriptionCardView() {
    if(isDescriptionInfoOpen){
      aboutApplicationView.closeAppDescriptionCardView();
      isDescriptionInfoOpen = false;
    }else {
      aboutApplicationView.openAppDescriptionCardView();
      isDescriptionInfoOpen = true;
    }
  }

  @Override
  public void openOrCloseAppAuthorCardView() {
    if(isAuthorInfoOpen){
      aboutApplicationView.closeAppAuthorCardView();
      isAuthorInfoOpen = false;
    }else {
      aboutApplicationView.openAppAuthorCardView();
      isAuthorInfoOpen = true;
    }
  }

  @Override
  public void openOrCloseAppVersionCardView() {
    if(isVersionInfoOpen){
      aboutApplicationView.closeAppVersionCardView();
      isVersionInfoOpen = false;
    }else {
      aboutApplicationView.openAppVersionCardView();
      isVersionInfoOpen = true;
    }
  }

  @Override
  public void openOrCloseAppErrorCardView() {
    if(isErrorInfoOpen){
      aboutApplicationView.closeAppErrorCardView();
      isErrorInfoOpen = false;
    }else {
      aboutApplicationView.openAppErrorCardView();
      isErrorInfoOpen = true;
    }
  }
}
