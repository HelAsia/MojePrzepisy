package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
  @BindView(R.id.login_action_button) Button loginButton;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;
  String loginString;
  String passwordString;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, MainRegisteredActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
      }
    });

    loginString = loginEditText.getText().toString();
    passwordString = passwordEditText.getText().toString();


  }
}
