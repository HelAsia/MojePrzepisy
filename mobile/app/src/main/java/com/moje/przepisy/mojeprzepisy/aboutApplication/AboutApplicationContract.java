package com.moje.przepisy.mojeprzepisy.aboutApplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

public interface AboutApplicationContract {

  interface View{
    void setToolbar();
    Context getContext();
    Boolean getLogged();
    void setLogged();
    void openAppDescriptionCardView();
    void closeAppDescriptionCardView();
    void openAppAuthorCardView();
    void closeAppAuthorCardView();
    void openAppVersionCardView();
    void closeAppVersionCardView();
    void openAppErrorCardView();
    void closeAppErrorCardView();
    void setDetailsOnClickListeners();
    void setDownArrow();
    void setRightArrow();
  }

  interface Presenter{
    void setFirstScreen();
    void openOrCloseAppDescriptionCardView();
    void openOrCloseAppAuthorCardView();
    void openOrCloseAppVersionCardView();
    void openOrCloseAppErrorCardView();
  }
}
