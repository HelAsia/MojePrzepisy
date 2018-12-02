package com.moje.przepisy.mojeprzepisy.log_out;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
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
public class LogoutPresenterUnitTest {
  private LogoutActivityView logoutActivityView;
  private LogoutPresenter mockPresenter;

  @Mock
  private UserRepository mockRepository;

  @Before
  public void setUp(){
    logoutActivityView = Robolectric.buildActivity(LogoutActivityView.class)
        .create().get();
    mockPresenter = new LogoutPresenter(logoutActivityView, mockRepository);
  }

  @Test
  public void onCancel_shouldStartMainCardsActivityView_loggedValueShouldBeTrue(){
    mockPresenter.onCancel();

    ShadowActivity shadowActivity = shadowOf(logoutActivityView);
    Intent startedIntent = shadowActivity.getNextStartedActivity();
    assertThat(startedIntent.getComponent().getClassName(),
        equalTo(MainCardsActivityView.class.getName()));
    assertThat(startedIntent.getBooleanExtra("LOGGED", false),
        equalTo(true));
  }

  @Test
  public void onSuccess_shouldStartMainCardsActivityView_loggedValueShouldBeFalse(){
    mockPresenter.onSuccess();

    ShadowActivity shadowActivity = shadowOf(logoutActivityView);
    Intent startedIntent = shadowActivity.getNextStartedActivity();
    assertThat(startedIntent.getComponent().getClassName(),
        equalTo(MainCardsActivityView.class.getName()));
    assertThat(startedIntent.getBooleanExtra("LOGGED", false),
        equalTo(false));
  }

  @Test
  public void onLogoutError_shouldShowErrorMessage(){
    mockPresenter.onLogoutError(anyString());

    TextView errorMessage = (TextView) logoutActivityView.findViewById(R.id.textViewErrorMessage);

    assertThat(errorMessage.getText().toString(), equalTo(anyString()));
    assertThat(errorMessage.getVisibility(), equalTo(View.VISIBLE));
  }
}
