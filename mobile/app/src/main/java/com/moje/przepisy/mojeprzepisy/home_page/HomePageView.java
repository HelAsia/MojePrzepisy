package com.moje.przepisy.mojeprzepisy.home_page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements.DisplayAllRecipeElementsActivityView;
import com.moje.przepisy.mojeprzepisy.log_in.LoginActivityView;
import com.moje.przepisy.mojeprzepisy.register.RegisterActivityView;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;
import com.moje.przepisy.mojeprzepisy.utils.Constant;

public class HomePageView extends AppCompatActivity {
  @BindView(R.id.register_button) Button registerButton;
  @BindView(R.id.login_button) Button loginButton;
  @BindView(R.id.no_login_button) Button noRegisteredButton;
  @BindView(R.id.DBGbutton) Button DBG;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);

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
        Intent intent = new Intent(HomePageView.this, MainCardsActivityView.class);
        intent.putExtra("LOGGED",false);
        startActivity(intent);
        HomePageView.this.finish();
      }
    });

    DBG.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(HomePageView.this, AddIngredientsActivityView.class);
        startActivity(intent);
      }
    });


  }
}
