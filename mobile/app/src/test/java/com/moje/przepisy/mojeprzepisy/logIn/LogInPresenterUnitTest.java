package com.moje.przepisy.mojeprzepisy.logIn;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.BuildConfig;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class LogInPresenterUnitTest {
  private LogInActivity logInActivity;
  private LogInPresenter mockPresenter;

  @Mock
  private UserRepository mockRepository;

  @Before
  public void setUp(){
    logInActivity = Robolectric.buildActivity(LogInActivity.class)
        .create().get();
    mockPresenter = new LogInPresenter(logInActivity, mockRepository);
  }

  @Test
  public void onSuccess_shouldStartMainCardsActivityView_loggedValueShouldBeTrue(){
    mockPresenter.onSuccess(anyInt());

    ShadowActivity shadowActivity = shadowOf(logInActivity);
    Intent startedIntent = shadowActivity.getNextStartedActivity();
    assertThat(startedIntent.getComponent().getClassName(),
        equalTo(MainCardsActivity.class.getName()));
    assertThat(startedIntent.getBooleanExtra("LOGGED", false),
        equalTo(true));
  }

  @Test
  public void onLoginAndPasswordError_shouldShowErrorMessage(){
    mockPresenter.onLoginAndPasswordError();
    TextView errorMessage = (TextView) logInActivity.findViewById(R.id.errorMessageTextView);

    assertThat(errorMessage.getText().toString(), equalTo(logInActivity.getResources()
        .getString(R.string.login_password_error_message)));
    assertThat(errorMessage.getVisibility(), equalTo(View.VISIBLE));
  }
}
