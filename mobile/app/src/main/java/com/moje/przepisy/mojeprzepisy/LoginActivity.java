package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {
  @BindView(R.id.login_action_button) Button loginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, MainRegisteredActivity.class);
        startActivity(intent);
      }
    });

  }
}
