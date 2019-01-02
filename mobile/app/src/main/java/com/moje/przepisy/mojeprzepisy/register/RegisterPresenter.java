package com.moje.przepisy.mojeprzepisy.register;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPresenter implements RegisterContract.Presenter,
        UserRepository.OnRegisterFinishedListener {
  private UserRepository userRepository;
  private RegisterContract.View registerView;

  public RegisterPresenter(RegisterContract.View registerView, UserRepository userRepository) {
    this.registerView = registerView;
    this.userRepository = userRepository;
  }

  @Override
  public void validateCredentials() {
    if(registerView != null) {
      userRepository.register(registerView.getName(), registerView.getLastName(),
                              registerView.getLogin(), registerView.getPassword(),
                              registerView.getEmail(),this);
    }
  }

  @Override
  public boolean onPasswordOrEmailError() {
    if(registerView != null) {
      if(!registerView.getPassword().equals(registerView.getRepeatedPassword())){
        registerView.setErrorTextView("Podane hasła nie są identyczne");
        return false;
      }else if(!registerView.getEmail().equals(registerView.getRepeatedEmail())){
        registerView.setErrorTextView("Podane emaile nie są identyczne");
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean onValidatePasswordError(String password) {
    if(registerView != null){
      String regex =
              "^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])" +
                      "(?=.*[A-Z])(?!.*\\s).{8,}$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(password);

      if(!matcher.matches()){
        String errorMessage = "Hasło musi zawierać małą literę, dużą literę i znak specjalny " +
                "oraz musi mieć przynajmniej 8 znaków!";
        registerView.setErrorTextView(errorMessage);
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean onValidateEmailError(String email) {
    if(registerView != null){
      String regex = "^[-\\w\\.]+@([-\\w]+\\.)+[a-z]+$";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(email);

      if(!matcher.matches()){
        registerView.setErrorTextView("Podany email nie jest poprawny");
        return false;
      }else {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onSuccess(int userId) {
    if(registerView != null) {
      saveUserIdInPreferences(userId);
      registerView.navigateToMainRegisteredActivity();
    }
  }

  @Override
  public void onError(String message) {
    if(registerView != null) {
      registerView.setErrorTextView(message);
    }
  }

  private void saveUserIdInPreferences(int userId){
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(registerView.getContext()).edit();
    editor.putInt(Constant.PREF_USER_ID, userId).apply();
    editor.commit();
  }
}
