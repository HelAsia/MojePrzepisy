package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
  @BindView(R.id.register_action_button) Button registerButton;
  @BindView(R.id.name_editText) EditText nameEditText;
  @BindView(R.id.last_name_editText) EditText lastNameEditText;
  @BindView(R.id.login_editText) EditText loginEditText;
  @BindView(R.id.password_editText) EditText passwordEditText;
  @BindView(R.id.repeated_password_editText) EditText repeatedPasswordEditText;
  @BindView(R.id.email_editText) EditText emailEditText;
  @BindView(R.id.repeated_email_editText) EditText repeatedEmailEditText;
  String nameString;
  String lastNameString;
  String loginString;
  String passwordString;
  String repeatedPasswordString;
  String emailString;
  String repeatedEmailString;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    ButterKnife.bind(this);

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(RegisterActivity.this, MainRegisteredActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
      }
    });

    nameString = nameEditText.getText().toString();
    lastNameString = lastNameEditText.getText().toString();
    loginString = loginEditText.getText().toString();
    passwordString = passwordEditText.getText().toString();
    repeatedPasswordString = repeatedPasswordEditText.getText().toString();
    emailString = emailEditText.getText().toString();
    repeatedEmailString = repeatedEmailEditText.getText().toString();



  }

}
