package com.moje.przepisy.mojeprzepisy.home_page;

import android.content.Context;
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
import com.moje.przepisy.mojeprzepisy.log_in.LoginActivityView;
import com.moje.przepisy.mojeprzepisy.register.RegisterActivityView;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class HomePageView extends AppCompatActivity {
  @BindView(R.id.register_button) Button registerButton;
  @BindView(R.id.login_button) Button loginButton;
  @BindView(R.id.no_login_button) Button noRegisteredButton;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);
    context = getApplicationContext();

    ButterKnife.bind(this);

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePageView.this, RegisterActivityView.class);
        startActivity(intent);
        HomePageView.this.finish();
      }
    });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePageView.this, LoginActivityView.class);
        startActivity(intent);
        HomePageView.this.finish();
      }
    });

    noRegisteredButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deleteUserIdFromPreferences();
        Intent intent = new Intent(HomePageView.this, MainCardsActivityView.class);
        intent.putExtra("LOGGED",false);
        startActivity(intent);
        HomePageView.this.finish();
      }
    });
  }

  public void deleteUserIdFromPreferences(){
    SharedPreferences.Editor ingredientsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    ingredientsEditor.remove(Constant.PREF_USER_ID);
    ingredientsEditor.apply();
  }
}
