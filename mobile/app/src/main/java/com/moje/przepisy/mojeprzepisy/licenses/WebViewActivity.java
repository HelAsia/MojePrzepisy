package com.moje.przepisy.mojeprzepisy.licenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.moje.przepisy.mojeprzepisy.R;

public class WebViewActivity extends AppCompatActivity {
  private WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);

    setToolbar();

    Intent i = getIntent();
    String url= i.getStringExtra("url");
    webView = (WebView) findViewById(R.id.webview);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient());
    webView.loadUrl(url);
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_licenses);
    toolbar.setSubtitle(R.string.licences);
    setSupportActionBar(toolbar);
  }
}
