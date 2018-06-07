package com.moje.przepisy.mojeprzepisy.login;

import static android.support.v4.util.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.MainRegisteredActivity;
import com.moje.przepisy.mojeprzepisy.R;

public class LoginActivityView extends AppCompatActivity implements LoginContract.View{

  private LoginContract.Presenter presenter;
  @BindView(R.id.login_action_button) Button loginButton;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;
  @BindView(R.id.remember_password_checkBox) CheckBox rememberPasswordCheckbox;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ButterKnife.bind(this);

    setLoginButton();

    rememberPasswordCheckbox.isChecked();

  }

  public void setLoginButton() {
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivityView.this, MainRegisteredActivity.class);
        startActivity(intent);
        LoginActivityView.this.finish();
      }
    });
  }

  @Override
  public String getLogin() {
    String loginString = loginEditText.getText().toString();
    return loginString;
  }

  @Override
  public String getPassword() {
    String passwordString = passwordEditText.getText().toString();
    return passwordString;
  }

  @Override
  public boolean getCheckboxValue(CheckBox rememberedPassword) {
    return rememberedPassword.isChecked();
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.start();
  }

  @Override
  public void setPresenter(@NonNull LoginContract.Presenter presenter) {
    presenter = checkNotNull(presenter);

  }
}
