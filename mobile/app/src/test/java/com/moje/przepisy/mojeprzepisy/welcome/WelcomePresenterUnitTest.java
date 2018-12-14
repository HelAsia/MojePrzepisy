package com.moje.przepisy.mojeprzepisy.welcome;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.BuildConfig;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.welcome.WelcomeRepository;
import com.moje.przepisy.mojeprzepisy.homePage.HomePageView;
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
public class WelcomePresenterUnitTest {
  private WelcomeActivity welcomeActivity;
  private WelcomePresenter mockPresenter;

  @Mock
  private WelcomeRepository mockRepository;

  @Before
  public void setUp(){
    welcomeActivity = Robolectric.buildActivity(WelcomeActivity.class)
        .create().get();
    mockPresenter = new WelcomePresenter(welcomeActivity, mockRepository);
  }

  @Test
  public void onLogged_shouldStartMainCardsActivityView_loggedValueShouldBeTrue(){
    mockPresenter.onLogged();

    ShadowActivity shadowActivity = shadowOf(welcomeActivity);
    Intent startedIntent = shadowActivity.getNextStartedActivity();
    assertThat(startedIntent.getComponent().getClassName(),
        equalTo(MainCardsActivity.class.getName()));
    assertThat(startedIntent.getBooleanExtra("LOGGED", false),
        equalTo(true));
  }

  @Test
  public void OnNotLogged_shouldStartHomePageView(){
    mockPresenter.onNotLogged();

    ShadowActivity shadowActivity = shadowOf(welcomeActivity);
    Intent startedIntent = shadowActivity.getNextStartedActivity();
    assertThat(startedIntent.getComponent().getClassName(),
        equalTo(HomePageView.class.getName()));
  }

  @Test
  public void onError_shouldShowErrorMessage(){
    mockPresenter.onError();

    TextView errorMessage = (TextView) welcomeActivity.findViewById(R.id.errorTextView);

    assertThat(errorMessage.getText().toString(), equalTo(welcomeActivity.getResources()
        .getString(R.string.server_connection_error)));
    assertThat(errorMessage.getVisibility(), equalTo(View.VISIBLE));
  }

}
