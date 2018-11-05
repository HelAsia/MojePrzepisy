package com.moje.przepisy.mojeprzepisy.app_description;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

public interface AppDescriptionContract {

  interface View{
    void setToolbar();
    Context getContext();
    CardView getAppDescriptionTitleCardView();
    CardView getAppAuthorTitleCardView();
    CardView getAppVersionTitleCardView();
    CardView getAppErrorTitleCardView();
    CardView getAppDescriptionTextPlCardView();
    CardView getAppAuthorTextPlCardView();
    CardView getAppVersionTextPlCardView();
    CardView getAppErrorTextPlCardView();
    CardView getAppDescriptionTextAngCardView();
    CardView getAppAuthorTextAngCardView();
    CardView getAppVersionTextAngCardView();
    CardView getAppErrorTextAngCardView();
    ImageView getAppDescriptionArrowImageView();
    ImageView getAppAuthorArrowImageView();
    ImageView getAppVersionArrowImageView();
    ImageView getAppErrorArrowImageView();
    void setDetailsOnClickListeners();
  }

  interface Presenter{
    void openAppDescriptionCardView();
    void closeAppDescriptionCardView();
    void openAppAuthorCardView();
    void closeAppAuthorCardView();
    void openAppVersionCardView();
    void closeAppVersionCardView();
    void openAppErrorCardView();
    void closeAppErrorCardView();
    void openOrCloseAppDescriptionCardView();
    void openOrCloseAppAuthorCardView();
    void openOrCloseAppVersionCardView();
    void openOrCloseAppErrorCardView();
    Drawable getDownArrow();
    Drawable getRightArrow();

  }
}
