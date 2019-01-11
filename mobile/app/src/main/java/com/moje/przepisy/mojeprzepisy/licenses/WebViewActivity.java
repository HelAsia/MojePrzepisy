package com.moje.przepisy.mojeprzepisy.licenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.moje.przepisy.mojeprzepisy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {
  @BindView(R.id.webview) WebView webView;
  @BindView(R.id.toolbar_licenses) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    ButterKnife.bind(this);

    setToolbar();
    setWebView();
  }

  @Override
  public void onBackPressed() {
    goToLicensesActivity();
  }

  private void setWebView(){
    Intent i = getIntent();
    String url= i.getStringExtra("url");
    webView.setWebViewClient(new WebViewClient());
    webView.loadUrl(url);
  }

  private void setToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    if(actionbar != null){
      actionbar.setDisplayHomeAsUpEnabled(true);
      actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }
  }

  public boolean onOptionsItemSelected(MenuItem item){
    goToLicensesActivity();
    return true;
  }

  private void goToLicensesActivity(){
    Intent intent = new Intent(this, LicensesActivity.class);
    startActivity(intent);
    WebViewActivity.this.finish();
  }
}
