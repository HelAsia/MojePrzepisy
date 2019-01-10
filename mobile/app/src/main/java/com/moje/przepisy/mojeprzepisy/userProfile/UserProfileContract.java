package com.moje.przepisy.mojeprzepisy.userProfile;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public interface UserProfileContract {
  interface View{
    Context getContext();
    void setToolbar();
    void setOnClickListeners();
    void setToastMessage(String message);
    void setMainUserPhotoImageView(String photo);
    void setLoginTextView(String login);
    void setNameEditText(String name);
    void setLastNameEditText(String lastName);
    void setEmailEditText(String email);
    void setPasswordEditText(String password);
    void setRepeatPasswordEditText(String repeatPassword);
    void setErrorMessageTextView(String errorMessage);
    void setRepeatLayoutVisible();
    String getName();
    String getLastName();
    String getEmail();
    String getPassword();
    String getRepeatPassword();
  }

  interface Presenter {
    void setFirstScreen();
    void getUserData();
    void sendEditDataToServer(String columnName);
    void sendImageDataToServer(ImageView image);
    void showLoginError();
    void showPasswordError();
    void showValidatePasswordError();
    void showValidateEmailError();
  }
}
