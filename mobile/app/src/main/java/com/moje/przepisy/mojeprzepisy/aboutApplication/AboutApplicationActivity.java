package com.moje.przepisy.mojeprzepisy.aboutApplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutApplicationActivity extends AppCompatActivity implements
        AboutApplicationContract.View{
  @BindView(R.id.toolbar_app_description) Toolbar toolbar;
  private AboutApplicationContract.Presenter presenter;
  private Boolean isLogged;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_app_view);
    ButterKnife.bind(this);

    presenter = new AboutApplicationPresenter(this);
    presenter.setFirstScreen();
  }

  @Override
  public void setToolbar() {
    toolbar.setSubtitle(R.string.app_description);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    if(actionbar != null){
      actionbar.setDisplayHomeAsUpEnabled(true);
      actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }
  }

  public boolean onOptionsItemSelected(MenuItem item){
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", isLogged);
    startActivity(intent);
    AboutApplicationActivity.this.finish();
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", isLogged);
    startActivity(intent);
    AboutApplicationActivity.this.finish();
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void setLogged() {
    if(getIntent().getExtras() != null){
      isLogged = getIntent().getExtras().getBoolean("isLogged");
    }
  }
}
