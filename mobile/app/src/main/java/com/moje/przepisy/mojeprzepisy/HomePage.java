package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.log_in.LoginActivityView;
import com.moje.przepisy.mojeprzepisy.register.RegisterActivityView;
import com.moje.przepisy.mojeprzepisy.without_registration.MainNoRegisteredActivity;

public class HomePage extends AppCompatActivity {
  @BindView(R.id.register_button) Button registerButton;
  @BindView(R.id.login_button) Button loginButton;
  @BindView(R.id.no_login_button) Button noRegisteredButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);

    ButterKnife.bind(this);

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePage.this, RegisterActivityView.class);
        startActivity(intent);
        HomePage.this.finish();
      }
    });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePage.this, LoginActivityView.class);
        startActivity(intent);
        HomePage.this.finish();
      }
    });

    noRegisteredButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePage.this, MainNoRegisteredActivity.class);
        startActivity(intent);
        HomePage.this.finish();
      }
    });



  }
}
