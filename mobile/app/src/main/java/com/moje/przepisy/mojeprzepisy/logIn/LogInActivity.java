package com.moje.przepisy.mojeprzepisy.logIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

public class LogInActivity extends AppCompatActivity implements LogInContract.View{
  private LogInContract.Presenter presenter;
  @BindView(R.id.errorMessageTextView)TextView errorMessageTextView;
  @BindView(R.id.login_action_button) Button loginButton;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    loginButton.setOnClickListener(view -> presenter.validateCredentials());

    presenter = new LogInPresenter(this, new UserRepository(getApplicationContext()));
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(LogInActivity.this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
    LogInActivity.this.finish();
  }

  @Override
  public void setErrorMessageTextView(String errorMessage) {
    errorMessageTextView.setVisibility(View.VISIBLE);
    errorMessageTextView.setText(errorMessage);
  }

  @Override
  public String getLogin() {
    return loginEditText.getText().toString();
  }

  @Override
  public String getPassword() {
    return passwordEditText.getText().toString();
  }
}
