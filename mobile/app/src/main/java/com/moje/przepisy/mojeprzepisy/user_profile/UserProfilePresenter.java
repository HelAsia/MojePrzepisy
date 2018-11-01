package com.moje.przepisy.mojeprzepisy.user_profile;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import com.squareup.picasso.Picasso;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfilePresenter implements UserProfileContract.Presenter, UserRepository.OnGetUserFinishedListener,
    UserRepository.OnEditUserFinishedListener, UserRepository.OnEditPhotoUserFinishedListener {
  private UserProfileContract.View userView;
  private UserRepository userRepository;
  BitmapConverter converter = new BitmapConverter();

  public UserProfilePresenter(UserProfileContract.View userView, UserRepository userRepository) {
    this.userView = userView;
    this.userRepository = userRepository;
  }

  @Override
  public void getUserData() {
    userRepository.getUser(this);
  }

  @Override
  public void sendEditDataToServer(String columnName, EditText columnValueEditText) {
    String dataToChange = columnValueEditText.getText().toString();
    userRepository.editUser(columnName, dataToChange, this);
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
    userView.getErrorMessageTextView().setText("Podany login istnieje już w bazie!");

  }

  @Override
  public void showPasswordError() {
    userView.getErrorMessageTextView().setText("Podane hasła nie są identyczne");
  }

  @Override
  public void showValidatePasswordError() {
    userView.getErrorMessageTextView().setText("Hasło musi zawierać małą literę, dużą literę i znak specjalny oraz musi mieć przynajmniej 8 znaków!");
  }

  @Override
  public void showValidateEmailError() {
    userView.getErrorMessageTextView().setText("Podany email nie jest poprawny");
  }

  @Override
  public void showRepeatPassword(LinearLayout layout) {
    userView.getEditRepeatPasswordLinearLayout().setVisibility(View.VISIBLE);
  }


  @Override
  public void onGetUserError() {
    Toast.makeText(userView.getContext(), "Błąd pobierania danych użytkownika!", Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void setUserValue(User user) {
    userView.getLoginTextView().setText(user.getLogin());
    userView.getNameEditText().setText(user.getFirstName());
    userView.getLastNameEditText().setText(user.getLastName());
    userView.getEmailEditText().setText(user.getEmail());
    userView.getPasswordEditText().setText(user.getPassword());
    userView.getRepeatPasswordEditText().setText(user.getPassword());
    if(user.getPhotoId() != 0){
      Picasso.get().load(BASE_URL + "recipe/photo/" + user.getPhotoId()).into(userView.getMainUserPhotoImageView());
    }
  }

  @Override
  public void onEditAndSendDataError() {
    Toast.makeText(userView.getContext(), "Błąd podczas wysyłąnia danych użytkownika!", Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void onSuccess() {
    Toast.makeText(userView.getContext(), "Zapisano zmiany w danych użytkownika!", Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public boolean onPasswordError() {
    if(userView != null) {
      if(!userView.getPasswordEditText().getText().toString().equals(userView.getRepeatPasswordEditText().getText().toString())){
        showPasswordError();
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean onValidatePasswordError(String password) {
    if(userView != null){
      String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])(?=.*[A-Z])(?!.*\\s).{8,}$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(password);

      if(!matcher.matches()){
        showValidatePasswordError();
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean onValidateEmailError(String email) {
    if(userView != null){
      String regex = "^[-\\w\\.]+@([-\\w]+\\.)+[a-z]+$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(email);

      if(!matcher.matches()){
        showValidateEmailError();
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onEditAndSendPhotoError() {
    Toast.makeText(userView.getContext(), "Błąd podczas wysyłania zdjęcia użytkownika!", Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void onPhotoSuccess() {
    Toast.makeText(userView.getContext(), "Zapisano zmiany w zdjęciu użytkownika!", Toast.LENGTH_SHORT)
        .show();
  }
}
