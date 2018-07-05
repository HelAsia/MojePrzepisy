package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.moje.przepisy.mojeprzepisy.HomePage;
import com.moje.przepisy.mojeprzepisy.with_registration.MainRegisteredActivity;
import com.moje.przepisy.mojeprzepisy.R;

public class WelcomeView extends AppCompatActivity implements WelcomeContract.View {
  private final int SPLASH_DISPLAY_LENGTH = 1500;
  private WelcomeContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_page);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        presenter.validateCredentialsBeforeMainMenu();

      }
    }, SPLASH_DISPLAY_LENGTH);

    presenter = new WelcomePresenter(this, new WelcomeRepository(getApplicationContext()));
  }

  @Override protected  void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(WelcomeView.this, MainRegisteredActivity.class);
    startActivity(intent);
    WelcomeView.this.finish();
  }

  @Override
  public void navigateToHomePage() {
    Intent intent = new Intent(WelcomeView.this, HomePage.class);
    WelcomeView.this.startActivity(intent);
    WelcomeView.this.finish();
  }

}