package com.moje.przepisy.mojeprzepisy.userProfile;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public interface UserProfileContract {
  interface View{
    Context getContext();
    void setOnClickListeners();
    ImageView getMainUserPhotoImageView();
    TextView getLoginTextView();
    EditText getNameEditText();
    ImageView getSaveNameImageView();
    EditText getLastNameEditText();
    ImageView getSaveLastNameImageView();
    EditText getEmailEditText();
    ImageView getEmailNameImageView();
    EditText getPasswordEditText();
    ImageView getSavePasswordImageView();
    EditText getRepeatPasswordEditText();
    ImageView getEditPasswordImageView();
    TextView getErrorMessageTextView();
    LinearLayout getEditRepeatPasswordLinearLayout();
    void loadImageFromCamera();
    void loadImageFromGallery();
  }

  interface Presenter{
    void getUserData();
    void sendEditDataToServer(String columnName, EditText columnValueEditText);
    void sendImageDataToServer(ImageView image);
    void showLoginError();
    void showPasswordError();
    void showValidatePasswordError();
    void showValidateEmailError();
    void showRepeatPassword(LinearLayout layout);
  }
}
