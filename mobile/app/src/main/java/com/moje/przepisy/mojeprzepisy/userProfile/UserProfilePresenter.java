package com.moje.przepisy.mojeprzepisy.userProfile;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import com.squareup.picasso.Picasso;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfilePresenter implements UserProfileContract.Presenter, UserRepository.OnGetUserFinishedListener,
    UserRepository.OnEditUserFinishedListener, UserRepository.OnEditPhotoUserFinishedListener {
  private UserProfileContract.View userView;
  private UserRepository userRepository;
  private BitmapConverter converter = new BitmapConverter();

  UserProfilePresenter(UserProfileContract.View userView, UserRepository userRepository) {
    this.userView = userView;
    this.userRepository = userRepository;
  }

  @Override
  public void setFirstScreen() {
    getUserData();
    userView.setOnClickListeners();
    userView.setToolbar();
  }

  @Override
  public void getUserData() {
    userRepository.getUser(this);
  }

  @Override
  public void sendEditDataToServer(String columnName) {
    switch (columnName) {
      case "email":
        if (!onValidateEmailError(userView.getEmail())) {
          userRepository.editUser(columnName, userView.getEmail(), this);
        }
        break;
      case "user_password":
        if (!onPasswordError() && !onValidatePasswordError(userView.getPassword())) {
          userRepository.editUser(columnName, userView.getLastName(), this);
        }
        break;
      case "first_name":
        userRepository.editUser(columnName, userView.getName(), this);
        break;
      case "last_name":
        userRepository.editUser(columnName, userView.getPassword(), this);
        break;
    }
  }

  @Override
  public void sendImageDataToServer(ImageView image) {
    BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
    Bitmap bitmap = drawable.getBitmap();
    String bitmapString = converter.BitMapToString(bitmap);
    userRepository.editPhotoUser(bitmapString, this);
  }

  @Override
  public void showLoginError() {
    String message = userView.getContext().getResources()
            .getString(R.string.user_profile_login_error);
    userView.setErrorMessageTextView(message);
  }

  @Override
  public void showPasswordError() {
    String message = userView.getContext().getResources()
            .getString(R.string.user_profile_password_error);
    userView.setErrorMessageTextView(message);
  }

  @Override
  public void showValidatePasswordError() {
    String message = userView.getContext().getResources()
            .getString(R.string.user_profile_validate_password_error);
    userView.setErrorMessageTextView(message);
  }

  @Override
  public void showValidateEmailError() {
    String message = userView.getContext().getResources()
            .getString(R.string.user_profile_validate_email_error);
    userView.setErrorMessageTextView(message);
  }

  @Override
  public void onError() {
    String message = "Błąd pobierania danych użytkownika!";
    userView.setToastMessage(message);
  }

  @Override
  public void setUserValue(User user) {
    userView.setLoginTextView(user.getLogin());
    userView.setNameEditText(user.getFirstName());
    userView.setLastNameEditText(user.getLastName());
    userView.setEmailEditText(user.getEmail());
    userView.setPasswordEditText(user.getPassword());
    userView.setRepeatPasswordEditText(user.getPassword());
    if(user.getPhotoId() != 0){
      String path = BASE_URL + "recipe/photo/" + user.getPhotoId();
      userView.setMainUserPhotoImageView(path);
    }
  }

  @Override
  public void onEditAndSendDataError() {
    String message = "Błąd podczas wysyłąnia danych użytkownika!";
    userView.setToastMessage(message);
  }

  @Override
  public void onSuccess() {
    String message = "Zapisano zmiany w danych użytkownika!";
    userView.setToastMessage(message);
  }

  @Override
  public boolean onPasswordError() {
    if(userView != null) {
      if(!userView.getPassword().equals(userView.getRepeatPassword())){
        showPasswordError();
        return true;
      }else {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean onValidatePasswordError(String password) {
    if(userView != null){
      String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])(?=.*[A-Z])(?!.*\\s).{8,}$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(password);

      if(!matcher.matches()){
        showValidatePasswordError();
        return true;
      }else {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean onValidateEmailError(String email) {
    if(userView != null){
      String regex = "^[-\\w\\.]+@([-\\w]+\\.)+[a-z]+$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(email);

      if(!matcher.matches()){
        showValidateEmailError();
        return true;
      }else {
        return false;
      }
    }
    return true;
  }

  @Override
  public void onEditAndSendPhotoError() {
    String message = "Błąd podczas wysyłania zdjęcia użytkownika!";
    userView.setToastMessage(message);
  }

  @Override
  public void onPhotoSuccess() {
    String message = "Zapisano zmiany w zdjęciu użytkownika!";
    userView.setToastMessage(message);
  }
}
