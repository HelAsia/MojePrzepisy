package com.moje.przepisy.mojeprzepisy.licenses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.License;
import java.util.List;

public class LicensesActivityView extends AppCompatActivity implements LicensesContract.View {
  private LicensesContract.Presenter presenter;
  private LicensesAdapter adapter;
  private Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_licenses);
    context = getApplicationContext();

    presenter = new LicensesPresenter(this);

    setToolbar();
    setRecyclerView(presenter.getLicensesList());

  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_licenses);
    toolbar.setSubtitle(R.string.licences);
    setSupportActionBar(toolbar);
  }

  @Override
  public void setRecyclerView(List<License> licenseList){
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.licencesDisplayRecyclerView);
    setAdapter(licenseList);
    LicensesAdapter adapter = getAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  public void setAdapter(List<License> licenseList){
    this.adapter = new LicensesAdapter(this, licenseList);
  }

  public LicensesAdapter getAdapter(){
    return adapter;
  }

  public void goToLicenseSource(String licenseUrl){
    Intent intent = new Intent(this, WebViewActivity.class);
    intent.putExtra("url", licenseUrl);
    startActivity(intent);
  }
}
