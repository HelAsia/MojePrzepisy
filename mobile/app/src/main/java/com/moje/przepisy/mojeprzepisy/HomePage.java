package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);

    Button registerButton;
    Button loginButton;
    Button noRegisteredButton;

    registerButton = (Button) findViewById(R.id.register_button);
    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePage.this, RegisterActivity.class);
        startActivity(intent);
      }
    });

    loginButton = (Button) findViewById(R.id.login_button);
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePage.this, LoginActivity.class);
        startActivity(intent);
      }
    });


    noRegisteredButton = (Button) findViewById(R.id.no_login_button);
    noRegisteredButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePage.this, MainNoRegisteredActivity.class);
        startActivity(intent);
      }
    });

  }
}
