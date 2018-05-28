package com.moje.przepisy.mojeprzepisy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomePage extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.welcome_page_activity);

    Thread timer = new Thread() {
      public void run() {
        try {
          sleep(1500);
        }catch (InterruptedException e){
          e.printStackTrace();
        }finally {

          Intent intent = new Intent();
          intent.setClass(WelcomePage.this, HomePage.class);
          startActivity(intent);
        }
      }
    };
    timer.start();
  }
}
