package com.moje.przepisy.mojeprzepisy.sendError;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.aboutApplication.AboutApplicationActivity;
import com.moje.przepisy.mojeprzepisy.aboutApplication.AboutApplicationContract;
import com.moje.przepisy.mojeprzepisy.aboutApplication.AboutApplicationPresenter;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendErrorActivity extends AppCompatActivity
        implements SendErrorContract.View{
    @BindView(R.id.toolbar_send_error) Toolbar toolbar;
    @BindView(R.id.send_email_button) Button sendEmaiButton;
    private Boolean isLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_error);
        ButterKnife.bind(this);

        SendErrorContract.Presenter presenter = new SendErrorPresenter(this);
        presenter.setFirstScreen();
    }

    @Override
    public void setToolbar() {
        toolbar.setSubtitle(R.string.app_error_title);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    @Override
    public void setButtonListener() {
        sendEmaiButton.setOnClickListener(view -> goToEmailApp());
    }

    private void goToEmailApp(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Błąd w aplikacji Moje Przepisy HelAsia");
        try {
            startActivity(Intent.createChooser(i, "Wyślij email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Brak aplikacji do wysyłania maili!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this, MainCardsActivity.class);
        intent.putExtra("isLogged", isLogged);
        startActivity(intent);
        SendErrorActivity.this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainCardsActivity.class);
        intent.putExtra("isLogged", isLogged);
        startActivity(intent);
        SendErrorActivity.this.finish();
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
