package com.moje.przepisy.mojeprzepisy.log_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.UserRepository;
import com.moje.przepisy.mojeprzepisy.with_registration.MainRegisteredActivity;
import com.moje.przepisy.mojeprzepisy.R;

public class LoginActivityView extends AppCompatActivity implements LoginContract.View, View.OnClickListener{

  private LoginContract.Presenter presenter;
  @BindView(R.id.errorMessageTextView)TextView errorMessageTextView;
  @BindView(R.id.login_action_button) Button loginButton;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ButterKnife.bind(this);

    loginButton.setOnClickListener(this);

    presenter = new LoginPresenter(this, new UserRepository(getApplicationContext()));
  }

  @Override protected  void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onClick(View view) {
    presenter.validateCredentials(getLogin(),getPassword());
  }

  @Override
  public void showLoginAndPasswordError() {
    errorMessageTextView.setText(getString(R.string.login_password_error_message));
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(LoginActivityView.this, MainRegisteredActivity.class);
    startActivity(intent);
    LoginActivityView.this.finish();
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
  public void onResume() {
    super.onResume();
  }
}
