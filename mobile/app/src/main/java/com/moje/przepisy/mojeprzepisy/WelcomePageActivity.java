package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomePageActivity extends AppCompatActivity {
  private final int SPLASH_DISPLAY_LENGTH = 1500;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_page);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

        Intent intent = new Intent(WelcomePageActivity.this, HomePage.class);
        WelcomePageActivity.this.startActivity(intent);
        WelcomePageActivity.this.finish();
      }
    }, SPLASH_DISPLAY_LENGTH);
  }
}