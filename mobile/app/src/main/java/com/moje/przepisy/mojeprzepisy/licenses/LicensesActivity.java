package com.moje.przepisy.mojeprzepisy.licenses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.categorySearch.CategorySearchActivity;
import com.moje.przepisy.mojeprzepisy.data.model.License;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LicensesActivity extends AppCompatActivity implements LicensesContract.View {
  @BindView(R.id.toolbar_licenses) Toolbar toolbar;
  @BindView(R.id.licencesDisplayRecyclerView) RecyclerView recyclerView;
  private boolean isLogged = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_licenses);
    ButterKnife.bind(this);

    LicensesContract.Presenter presenter = new LicensesPresenter(this);
    presenter.setFirstScreen();
  }

  @Override
  public void setIsLogged() {
    if(getIntent().getExtras() != null){
      isLogged = getIntent().getExtras().getBoolean("isLogged");
    }
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void setToolbar() {
    toolbar.setSubtitle(R.string.licences);
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
    LicensesActivity.this.finish();
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", isLogged);
    startActivity(intent);
    LicensesActivity.this.finish();
  }
  @Override
  public void setRecyclerView(List<License> licenseList){
    LicensesAdapter adapter = new LicensesAdapter(this, licenseList);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  public void goToLicenseSource(String licenseUrl){
    Intent intent = new Intent(this, WebViewActivity.class);
    intent.putExtra("url", licenseUrl);
    startActivity(intent);
  }
}
