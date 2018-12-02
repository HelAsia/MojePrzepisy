package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.welcome.WelcomeRepository;
import com.moje.przepisy.mojeprzepisy.home_page.HomePageView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;

public class WelcomeActivityView extends AppCompatActivity implements WelcomeContract.View {
  private final int SPLASH_DISPLAY_LENGTH = 1500;
  private WelcomeContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_page);
    context = getApplicationContext();

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
    Intent intent = new Intent(WelcomeActivityView.this, MainCardsActivityView.class);
    intent.putExtra("LOGGED",true);
    startActivity(intent);
    WelcomeActivityView.this.finish();
  }

  @Override
  public void navigateToHomePage() {
    Intent intent = new Intent(WelcomeActivityView.this, HomePageView.class);
    WelcomeActivityView.this.startActivity(intent);
    WelcomeActivityView.this.finish();
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void errorMessage() {
    TextView errorTextView = (TextView) findViewById(R.id.errorTextView);
    errorTextView.setVisibility(View.VISIBLE);
    errorTextView.setText(R.string.server_connection_error);
  }
}