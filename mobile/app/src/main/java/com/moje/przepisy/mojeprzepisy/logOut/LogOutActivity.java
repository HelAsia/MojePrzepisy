package com.moje.przepisy.mojeprzepisy.logOut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;

public class LogOutActivity extends AppCompatActivity implements LogOutContract.View {
  private LogOutContract.Presenter presenter;
  @BindView(R.id.logout_button_yes) Button yesButton;
  @BindView(R.id.logout_button_no) Button noButton;
  @BindView(R.id.textViewErrorMessage) TextView errorMessageTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_logout_view);
    ButterKnife.bind(this);

    presenter = new LogOutPresenter(this, new UserRepository(getApplicationContext()));
    setButtonListeners();
  }

  private void setButtonListeners(){
    yesButton.setOnClickListener(view -> presenter.validateCredentials());
    noButton.setOnClickListener(view -> presenter.onCancel());
  }

  @Override
  public void navigateToMainLoggedCardsActivity() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
    LogOutActivity.this.finish();
  }

  @Override
  public void navigateToUnLoggedMainCardsActivity() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged",false);
    startActivity(intent);
    LogOutActivity.this.finish();
  }

  @Override
  public void setErrorMessage(String message) {
    errorMessageTextView.setVisibility(View.VISIBLE);
    errorMessageTextView.setText(message);
  }
}
