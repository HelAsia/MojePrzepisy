package com.moje.przepisy.mojeprzepisy.aboutApplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.moje.przepisy.mojeprzepisy.R;

public class AboutApplicationActivity extends AppCompatActivity implements AboutApplicationContract.View,
    OnClickListener {
  AboutApplicationContract.Presenter presenter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_app_view);
    context = getApplicationContext();

    presenter = new AboutApplicationPresenter(this);
    setDetailsOnClickListeners();
    setToolbar();
  }

  @Override
  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_app_description);
    toolbar.setSubtitle(R.string.app_description);
    setSupportActionBar(toolbar);
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.app_description_title_my_card_view_layout){
      presenter.openOrCloseAppDescriptionCardView();
    }else if(view.getId() == R.id.app_author_title_my_card_view_layout){
      presenter.openOrCloseAppAuthorCardView();
    }else if(view.getId() == R.id.app_version_title_my_card_view_layout){
      presenter.openOrCloseAppVersionCardView();
    }else if(view.getId() == R.id.app_error_title_my_card_view_layout){
      presenter.openOrCloseAppErrorCardView();
    }
  }

  @Override
  public CardView getAppDescriptionTitleCardView() {
    return (CardView)findViewById(R.id.app_description_title_my_card_view_layout);
  }

  @Override
  public CardView getAppAuthorTitleCardView() {
    return (CardView)findViewById(R.id.app_author_title_my_card_view_layout);
  }

  @Override
  public CardView getAppVersionTitleCardView() {
    return (CardView)findViewById(R.id.app_version_title_my_card_view_layout);
  }

  @Override
  public CardView getAppErrorTitleCardView() {
    return (CardView)findViewById(R.id.app_error_title_my_card_view_layout);
  }

  @Override
  public CardView getAppDescriptionTextPlCardView() {
    return (CardView)findViewById(R.id.app_description_text_pl_my_card_view_layout);
  }

  @Override
  public CardView getAppAuthorTextPlCardView() {
    return (CardView)findViewById(R.id.app_author_text_pl_my_card_view_layout);
  }

  @Override
  public CardView getAppVersionTextPlCardView() {
    return (CardView)findViewById(R.id.app_version_text_pl_my_card_view_layout);
  }

  @Override
  public CardView getAppErrorTextPlCardView() {
    return (CardView)findViewById(R.id.app_error_text_pl_my_card_view_layout);
  }

  @Override
  public CardView getAppDescriptionTextAngCardView() {
    return (CardView)findViewById(R.id.app_description_text_ang_my_card_view_layout);
  }

  @Override
  public CardView getAppAuthorTextAngCardView() {
    return (CardView)findViewById(R.id.app_author_text_ang_my_card_view_layout);
  }

  @Override
  public CardView getAppVersionTextAngCardView() {
    return (CardView)findViewById(R.id.app_version_text_ang_my_card_view_layout);
  }

  @Override
  public CardView getAppErrorTextAngCardView() {
    return (CardView)findViewById(R.id.app_error_text_ang_my_card_view_layout);
  }

  @Override
  public ImageView getAppDescriptionArrowImageView() {
    return (ImageView)findViewById(R.id.app_description_play_arrow);
  }

  @Override
  public ImageView getAppAuthorArrowImageView() {
    return (ImageView)findViewById(R.id.app_author_play_arrow);
  }

  @Override
  public ImageView getAppVersionArrowImageView() {
    return (ImageView)findViewById(R.id.app_version_play_arrow);
  }

  @Override
  public ImageView getAppErrorArrowImageView() {
    return (ImageView)findViewById(R.id.app_error_play_arrow);
  }

  @Override
  public void setDetailsOnClickListeners() {
    getAppDescriptionTitleCardView().setOnClickListener(this);
    getAppAuthorTitleCardView().setOnClickListener(this);
    getAppVersionTitleCardView().setOnClickListener(this);
    getAppErrorTitleCardView().setOnClickListener(this);
  }


}
