package com.moje.przepisy.mojeprzepisy.user_profile;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

public interface UserProfileContract {
  interface View{
    Context getContext();
    ImageView getMainUserPhotoImageView();
    ImageView getLoadFromGalleryImageView();
    ImageView getLoadFromCameraImageView();
    ImageView getLoadFromUrlImageView();
    EditText getLoginEditText();
    ImageView getSaveLoginImageView();
    EditText getNameEditText();
    ImageView getSaveNameImageView();
    EditText getLastNameEditText();
    ImageView getSaveLastNameImageView();
    EditText getEmailEditText();
    ImageView getEmailtNameImageView();
    EditText getPasswordEditText();
    ImageView getSavePasswordImageView();
    EditText getRepeatPasswordEditText();
    ImageView getRepeatPasswordImageView();
  }

  interface Presenter{
    void sendEditDataToServer(EditText editTextIcon);
    void sendImageDataToServer(ImageView image);
  }
}
