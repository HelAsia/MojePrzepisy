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

public class LogInActivity extends AppCompatActivity implements LogInContract.View, View.OnClickListener{
  private LogInContract.Presenter presenter;
  @BindView(R.id.errorMessageTextView)TextView errorMessageTextView;
  @BindView(R.id.login_action_button) Button loginButton;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    context = getApplicationContext();

    ButterKnife.bind(this);

    loginButton.setOnClickListener(this);

    presenter = new LogInPresenter(this, new UserRepository(getApplicationContext()));
  }

  @Override
  protected  void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onClick(View view) {
    presenter.validateCredentials();
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(LogInActivity.this, MainCardsActivity.class);
    intent.putExtra("LOGGED",true);
    startActivity(intent);
    LogInActivity.this.finish();
  }

  @Override
  public TextView getErrorMessageTextView() {
    return errorMessageTextView;
  }

  @Override
  public EditText getLoginEditText() {
    return loginEditText;
  }

  @Override
  public EditText getPasswordEditText() {
    return passwordEditText;
  }
}
