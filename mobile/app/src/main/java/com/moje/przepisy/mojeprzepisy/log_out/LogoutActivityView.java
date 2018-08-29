package com.moje.przepisy.mojeprzepisy.log_out;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;

public class LogoutActivityView extends AppCompatActivity implements LogoutContract.View,
    View.OnClickListener {

  private LogoutContract.Presenter presenter;
  @BindView(R.id.logout_button_yes) Button yesButton;
  @BindView(R.id.logout_button_no) Button noButton;
  @BindView(R.id.textViewErrorMessage) TextView errorMessageTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_logout_view);

    ButterKnife.bind(this);

    yesButton.setOnClickListener(this);
    noButton.setOnClickListener(this);

    presenter = new LogoutPresenter(this, new UserRepository(getApplicationContext()));
  }

  @Override
  protected  void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.logout_button_yes:
        presenter.validateCredentials();
        break;
      case R.id.logout_button_no:
        navigateToMainLoggedCardsActivity();
        break;
      default:
        break;
    }
  }

  @Override
  public void navigateToMainLoggedCardsActivity() {
    Intent intent = new Intent(LogoutActivityView.this, MainCardsActivityView.class);
    intent.putExtra("LOGGED",true);
    startActivity(intent);
    LogoutActivityView.this.finish();
  }

  @Override
  public void navigateToUnloggedMainCardsActivity() {
    Intent intent = new Intent(LogoutActivityView.this, MainCardsActivityView.class);
    intent.putExtra("LOGGED",false);
    startActivity(intent);
    LogoutActivityView.this.finish();
  }

  @Override
  public void showLogoutError(String message) {
    errorMessageTextView.setText(message);

   // getString(R.string.logout_error_message) +
  }
}
