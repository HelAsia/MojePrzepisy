package com.moje.przepisy.mojeprzepisy.register;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
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

    registerButton.setOnClickListener(view -> presenter.validateCredentials());

    presenter = new RegisterPresenter(this, new UserRepository(getApplicationContext()));
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(RegisterActivity.this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
    RegisterActivity.this.finish();
  }

  @Override
  public Context getContext() {
    return this;
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
  public void setErrorTextView(String message) {
    errorMessageTextView.setText(message);
  }
}
