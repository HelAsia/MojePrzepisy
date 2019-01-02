package com.moje.przepisy.mojeprzepisy.homePage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.logIn.LogInActivity;
import com.moje.przepisy.mojeprzepisy.register.RegisterActivity;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class HomePageView extends AppCompatActivity {
  @BindView(R.id.register_button) Button registerButton;
  @BindView(R.id.login_button) Button loginButton;
  @BindView(R.id.no_login_button) Button noRegisteredButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);
    ButterKnife.bind(this);

    setOnClickListeners();
  }

  private void setOnClickListeners(){
    registerButton.setOnClickListener(view -> {
      Intent intent = new Intent(this, RegisterActivity.class);
      startActivity(intent);
      HomePageView.this.finish();
    });

    loginButton.setOnClickListener(view -> {
      Intent intent = new Intent(this, LogInActivity.class);
      startActivity(intent);
      HomePageView.this.finish();
    });

    noRegisteredButton.setOnClickListener(view -> {
      deleteUserIdFromPreferences();
      Intent intent = new Intent(this, MainCardsActivity.class);
      intent.putExtra("isLogged",false);
      startActivity(intent);
      HomePageView.this.finish();
    });
  }

  private void deleteUserIdFromPreferences(){
    SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    ingredientsEditor.remove(Constant.PREF_USER_ID);
    ingredientsEditor.apply();
  }
}
