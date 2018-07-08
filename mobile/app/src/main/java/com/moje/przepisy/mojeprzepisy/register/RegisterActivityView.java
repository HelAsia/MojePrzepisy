package com.moje.przepisy.mojeprzepisy.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.UserRepository;
import com.moje.przepisy.mojeprzepisy.with_registration.MainRegisteredActivity;
import com.moje.przepisy.mojeprzepisy.R;

public class RegisterActivityView extends AppCompatActivity implements RegisterContract.View, View.OnClickListener {

  private RegisterContract.Presenter presenter;
  @BindView(R.id.errorMessageTextView)TextView errorMessageTextView;
  @BindView(R.id.register_action_button) Button registerButton;
  @BindView(R.id.name_editText) EditText nameEditText;
  @BindView(R.id.last_name_editText) EditText lastNameEditText;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;
  @BindView(R.id.repeated_password_editText) EditText repeatedPasswordEditText;
  @BindView(R.id.email_editText) EditText emailEditText;
  @BindView(R.id.repeated_email_editText) EditText repeatedEmailEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    ButterKnife.bind(this);

    registerButton.setOnClickListener(this);

    presenter = new RegisterPresenter(this, new UserRepository(getApplicationContext()));
  }

  @Override protected  void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onClick(View view) {
    presenter.validateCredentials(getName(),getLastName(),getLogin(),getPassword(),getEmail());
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(RegisterActivityView.this, MainRegisteredActivity.class);
    startActivity(intent);
    RegisterActivityView.this.finish();

  }

  @Override
  public String getName() {
    return nameEditText.getText().toString();
  }

  @Override
  public String getLastName() {
    return lastNameEditText.getText().toString();
  }

  @Override
  public String getLogin() {
    return loginEditText.getText().toString();
  }

  @Override
  public String getPassword() {
    return passwordEditText.getText().toString();
  }

  @Override
  public String getRepeatedPassword() {
    return repeatedPasswordEditText.getText().toString();
  }

  @Override
  public String getEmail() {
    return emailEditText.getText().toString();
  }

  @Override
  public String getRepeatedEmail() {
    return repeatedEmailEditText.getText().toString();
  }

  @Override
  public void showLoginError() {
    errorMessageTextView.setText("Podany login istnieje już w bazie!");

  }

  @Override
  public void showPasswordError() {
    errorMessageTextView.setText("Podane hasła nie są identyczne");
  }

  @Override
  public void showValidatePasswordError() {
    errorMessageTextView.setText("Hasło musi zawierać małą literę, dużą literę i znak specjalny oraz musi mieć przynajmniej 8 znaków!");
  }

  @Override
  public void showValidateEmailError() {
    errorMessageTextView.setText("Podany email nie jest poprawny");
  }

  @Override
  public void showOtherError(String message) {
    errorMessageTextView.setText(message);
  }

  @Override
  public void showEmailError() {
    errorMessageTextView.setText("Podane emaile nie są identyczne");
  }

  @Override
  public void onResume() {
    super.onResume();
  }
}
