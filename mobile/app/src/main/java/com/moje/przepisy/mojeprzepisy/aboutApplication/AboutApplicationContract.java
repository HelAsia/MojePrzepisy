package com.moje.przepisy.mojeprzepisy.aboutApplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

public interface AboutApplicationContract {

  interface View{
    void setToolbar();
    Context getContext();
    void setLogged();
  }

  interface Presenter{
    void setFirstScreen();
  }
}
