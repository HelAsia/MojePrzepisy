package com.moje.przepisy.mojeprzepisy.log_in;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.BuildConfig;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.ui.MainCardsActivityView;
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
public class LoginPresenterUnitTest {
  private LoginActivityView loginActivityView;
  private LoginPresenter mockPresenter;

  @Mock
  private UserRepository mockRepository;

  @Before
  public void setUp(){
    loginActivityView = Robolectric.buildActivity(LoginActivityView.class)
        .create().get();
    mockPresenter = new LoginPresenter(loginActivityView, mockRepository);
  }

  @Test
  public void onSuccess_shouldStartMainCardsActivityView_loggedValueShouldBeTrue(){
    mockPresenter.onSuccess(anyInt());

    ShadowActivity shadowActivity = shadowOf(loginActivityView);
    Intent startedIntent = shadowActivity.getNextStartedActivity();
    assertThat(startedIntent.getComponent().getClassName(),
        equalTo(MainCardsActivityView.class.getName()));
    assertThat(startedIntent.getBooleanExtra("LOGGED", false),
        equalTo(true));
  }

  @Test
  public void onLoginAndPasswordError_shouldShowErrorMessage(){
    mockPresenter.onLoginAndPasswordError();
    TextView errorMessage = (TextView) loginActivityView.findViewById(R.id.errorMessageTextView);

    assertThat(errorMessage.getText().toString(), equalTo(loginActivityView.getResources()
        .getString(R.string.login_password_error_message)));
    assertThat(errorMessage.getVisibility(), equalTo(View.VISIBLE));
  }
}
