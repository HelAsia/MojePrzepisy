package com.moje.przepisy.mojeprzepisy.aboutApplication;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.moje.przepisy.mojeprzepisy.R;

public class AboutApplicationPresenter implements AboutApplicationContract.Presenter {
  private AboutApplicationContract.View appDescriptionView;
  private Boolean isDescriptionInfoOpen = false;
  private Boolean isAuthorInfoOpen = false;
  private Boolean isVersionInfoOpen = false;
  private Boolean isErrorInfoOpen = false;

  public AboutApplicationPresenter(AboutApplicationContract.View appDescriptionView){
    this.appDescriptionView = appDescriptionView;
  }

  @Override
  public void openAppDescriptionCardView() {
    appDescriptionView.getAppDescriptionArrowImageView().setImageDrawable(getDownArrow());
    appDescriptionView.getAppDescriptionTextPlCardView().setVisibility(View.VISIBLE);
    appDescriptionView.getAppDescriptionTextAngCardView().setVisibility(View.VISIBLE);
    isDescriptionInfoOpen = true;
  }

  @Override
  public void closeAppDescriptionCardView() {
    appDescriptionView.getAppDescriptionArrowImageView().setImageDrawable(getRightArrow());
    appDescriptionView.getAppDescriptionTextPlCardView().setVisibility(View.GONE);
    appDescriptionView.getAppDescriptionTextAngCardView().setVisibility(View.GONE);
    isDescriptionInfoOpen = false;
  }

  @Override
  public void openAppAuthorCardView() {
    appDescriptionView.getAppAuthorArrowImageView().setImageDrawable(getDownArrow());
    appDescriptionView.getAppAuthorTextPlCardView().setVisibility(View.VISIBLE);
    appDescriptionView.getAppAuthorTextAngCardView().setVisibility(View.VISIBLE);
    isAuthorInfoOpen = true;
  }

  @Override
  public void closeAppAuthorCardView() {
    appDescriptionView.getAppAuthorArrowImageView().setImageDrawable(getRightArrow());
    appDescriptionView.getAppAuthorTextPlCardView().setVisibility(View.GONE);
    appDescriptionView.getAppAuthorTextAngCardView().setVisibility(View.GONE);
    isAuthorInfoOpen = false;
  }

  @Override
  public void openAppVersionCardView() {
    appDescriptionView.getAppVersionArrowImageView().setImageDrawable(getDownArrow());
    appDescriptionView.getAppVersionTextPlCardView().setVisibility(View.VISIBLE);
    appDescriptionView.getAppVersionTextAngCardView().setVisibility(View.VISIBLE);
    isVersionInfoOpen = true;
  }

  @Override
  public void closeAppVersionCardView() {
    appDescriptionView.getAppVersionArrowImageView().setImageDrawable(getRightArrow());
    appDescriptionView.getAppVersionTextPlCardView().setVisibility(View.GONE);
    appDescriptionView.getAppVersionTextAngCardView().setVisibility(View.GONE);
    isVersionInfoOpen = false;
  }

  @Override
  public void openAppErrorCardView() {
    appDescriptionView.getAppErrorArrowImageView().setImageDrawable(getDownArrow());
    appDescriptionView.getAppErrorTextPlCardView().setVisibility(View.VISIBLE);
    appDescriptionView.getAppErrorTextAngCardView().setVisibility(View.VISIBLE);
    isErrorInfoOpen = true;
  }

  @Override
  public void closeAppErrorCardView() {
    appDescriptionView.getAppErrorArrowImageView().setImageDrawable(getRightArrow());
    appDescriptionView.getAppErrorTextPlCardView().setVisibility(View.GONE);
    appDescriptionView.getAppErrorTextAngCardView().setVisibility(View.GONE);
    isErrorInfoOpen = false;
  }

  @Override
  public void openOrCloseAppDescriptionCardView() {
    if(isDescriptionInfoOpen){
      closeAppDescriptionCardView();
    }else {
      openAppDescriptionCardView();
    }
  }

  @Override
  public void openOrCloseAppAuthorCardView() {
    if(isAuthorInfoOpen){
      closeAppAuthorCardView();
    }else {
      openAppAuthorCardView();
    }
  }

  @Override
  public void openOrCloseAppVersionCardView() {
    if(isVersionInfoOpen){
      closeAppVersionCardView();
    }else {
      openAppVersionCardView();
    }
  }

  @Override
  public void openOrCloseAppErrorCardView() {
    if(isErrorInfoOpen){
      closeAppErrorCardView();
    }else {
      openAppErrorCardView();
    }
  }

  @Override
  public Drawable getDownArrow() {
    return appDescriptionView.getContext().getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
  }

  @Override
  public Drawable getRightArrow() {
    return appDescriptionView.getContext().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp);
  }

}
