package com.moje.przepisy.mojeprzepisy.log_out;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.UserRepository;

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

  }

  @Override
  public void navigateToMainCardsActivity() {

  }

  @Override
  public void showLogoutError(String message) {
    errorMessageTextView.setText(getString(R.string.logout_error_message) + message);


  }
}
