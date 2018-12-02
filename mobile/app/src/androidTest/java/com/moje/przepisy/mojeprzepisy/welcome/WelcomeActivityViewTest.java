package com.moje.przepisy.mojeprzepisy.welcome;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.matcher.ViewMatchers.Visibility;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.moje.przepisy.mojeprzepisy.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityViewTest {

  @Rule
  public ActivityTestRule<WelcomeActivityView> welcomeActivityViewActivityTestRule =
      new ActivityTestRule<WelcomeActivityView>(WelcomeActivityView.class);

  @Test
  public void mainScreen_firstLoad(){
    onView(withId(R.id.logoImageView)).check(matches(isDisplayed()));
    onView(withId(R.id.errorTextView)).check(matches(withEffectiveVisibility(Visibility.GONE)));
  }
}
