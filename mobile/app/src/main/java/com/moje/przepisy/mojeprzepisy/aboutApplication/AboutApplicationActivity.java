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
  @BindView(R.id.app_description_title_my_card_view_layout) CardView descriptionTitle;
  @BindView(R.id.app_author_title_my_card_view_layout) CardView authorTitle;
  @BindView(R.id.app_version_title_my_card_view_layout) CardView versionTitle;
  @BindView(R.id.app_error_title_my_card_view_layout) CardView errorTitle;
  @BindView(R.id.app_description_text_pl_my_card_view_layout) CardView descriptionTextPl;
  @BindView(R.id.app_author_text_pl_my_card_view_layout) CardView authorTextPl;
  @BindView(R.id.app_version_text_pl_my_card_view_layout) CardView versionTextPl;
  @BindView(R.id.app_error_text_pl_my_card_view_layout) CardView errorTextPl;
  @BindView(R.id.app_description_text_ang_my_card_view_layout) CardView descriptionTextAng;
  @BindView(R.id.app_author_text_ang_my_card_view_layout) CardView authorTextAng;
  @BindView(R.id.app_version_text_ang_my_card_view_layout) CardView versionTextAng;
  @BindView(R.id.app_error_text_ang_my_card_view_layout) CardView errorTextAng;
  @BindView(R.id.app_description_play_arrow) ImageView descriptionPlayArrow;
  @BindView(R.id.app_author_play_arrow) ImageView authorPlayArrow;
  @BindView(R.id.app_version_play_arrow) ImageView versionPlayArrow;
  @BindView(R.id.app_error_play_arrow) ImageView errorPlayArrow;
  private AboutApplicationContract.Presenter presenter;
  private Boolean isLogged;
  private Drawable downArrow;
  private Drawable rightArrow;

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

  @Override
  public void openAppDescriptionCardView() {
    descriptionPlayArrow.setImageDrawable(downArrow);
    descriptionTextPl.setVisibility(View.VISIBLE);
    descriptionTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppDescriptionCardView() {
    descriptionPlayArrow.setImageDrawable(rightArrow);
    descriptionTextPl.setVisibility(View.GONE);
    descriptionTextAng.setVisibility(View.GONE);
  }

  @Override
  public void openAppAuthorCardView() {
    authorPlayArrow.setImageDrawable(downArrow);
    authorTextPl.setVisibility(View.VISIBLE);
    authorTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppAuthorCardView() {
    authorPlayArrow.setImageDrawable(rightArrow);
    authorTextPl.setVisibility(View.GONE);
    authorTextAng.setVisibility(View.GONE);
  }

  @Override
  public void openAppVersionCardView() {
    versionPlayArrow.setImageDrawable(downArrow);
    versionTextPl.setVisibility(View.VISIBLE);
    versionTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppVersionCardView() {
    versionPlayArrow.setImageDrawable(rightArrow);
    versionTextPl.setVisibility(View.GONE);
    versionTextAng.setVisibility(View.GONE);
  }

  @Override
  public void openAppErrorCardView() {
    errorPlayArrow.setImageDrawable(downArrow);
    errorTextPl.setVisibility(View.VISIBLE);
    errorTextAng.setVisibility(View.VISIBLE);
  }

  @Override
  public void closeAppErrorCardView() {
    errorPlayArrow.setImageDrawable(rightArrow);
    errorTextPl.setVisibility(View.GONE);
    errorTextAng.setVisibility(View.GONE);
  }

  @Override
  public void setDetailsOnClickListeners() {
    descriptionTitle.setOnClickListener(v ->
            presenter.openOrCloseAppDescriptionCardView());
    authorTitle.setOnClickListener(v ->
            presenter.openOrCloseAppAuthorCardView());
    versionTitle.setOnClickListener(v ->
            presenter.openOrCloseAppVersionCardView());
    errorTitle.setOnClickListener(v ->
            presenter.openOrCloseAppErrorCardView());
  }

  @Override
  public void setDownArrow() {
    downArrow = this.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
  }

  @Override
  public void setRightArrow() {
    rightArrow = this.getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp);
  }
}
