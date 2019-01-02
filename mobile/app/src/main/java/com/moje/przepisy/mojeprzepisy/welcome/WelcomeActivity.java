package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.data.repositories.welcome.WelcomeRepository;
import com.moje.przepisy.mojeprzepisy.homePage.HomePageView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View {
  @BindView(R.id.errorTextView) TextView errorTextView;
  private WelcomeContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_page);
    ButterKnife.bind(this);

    presenter = new WelcomePresenter(this, new WelcomeRepository(getApplicationContext()));

    new Handler().postDelayed(() -> presenter.validateCredentialsBeforeMainMenu(), 1500);
  }

  @Override
  public void navigateToMainRegisteredActivity() {
    Intent intent = new Intent(WelcomeActivity.this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
    WelcomeActivity.this.finish();
  }

  @Override
  public void navigateToHomePage() {
    Intent intent = new Intent(WelcomeActivity.this, HomePageView.class);
    WelcomeActivity.this.startActivity(intent);
    WelcomeActivity.this.finish();
  }

  @Override
  public void setErrorTextView(String errorMessage) {
    errorTextView.setVisibility(View.VISIBLE);
    errorTextView.setText(errorMessage);
  }

  @Override
  public Context getContext() {
    return this;
  }
}