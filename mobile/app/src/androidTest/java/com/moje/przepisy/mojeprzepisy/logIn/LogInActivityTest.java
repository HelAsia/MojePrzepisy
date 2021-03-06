package com.moje.przepisy.mojeprzepisy.logIn;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.matcher.ViewMatchers.Visibility;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.moje.przepisy.mojeprzepisy.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogInActivityTest {
  private static final String EMPTY_STRING = "";
  private static final String TESTING_DATA = "test";

  @Rule
  public ActivityTestRule<LogInActivity> loginActivityViewActivityTestRule =
      new ActivityTestRule<LogInActivity>(LogInActivity.class);

  @Test
  public void mainScreen_firstLoad() {
    onView(withId(R.id.errorMessageTextView)).check(matches(ViewMatchers.withText(EMPTY_STRING)));

    onView(withId(R.id.login_textView)).check(matches(isDisplayed()));
    onView(withId(R.id.login_editText)).check(matches(ViewMatchers.withText(EMPTY_STRING)));
    onView(withId(R.id.login_editText)).check(matches(ViewMatchers.withHint(R.string.your_login)));

    onView(withId(R.id.password_textView)).check(matches(isDisplayed()));
    onView(withId(R.id.password_editText)).check(matches(ViewMatchers.withText(EMPTY_STRING)));
    onView(withId(R.id.password_editText))
        .check(matches(ViewMatchers.withHint(R.string.your_password)));

    onView(withId(R.id.login_action_button)).check(matches(isDisplayed()));
    onView(withId(R.id.errorMessageTextView)).check(matches(withEffectiveVisibility(Visibility.GONE)));
  }

  @Test
  public void goodDataInEditText() {
    onView(withId(R.id.login_editText))
        .perform(typeText(TESTING_DATA)).check(matches(ViewMatchers.withText(TESTING_DATA)));

    onView(withId(R.id.password_editText))
        .perform(typeText(TESTING_DATA)).check(matches(ViewMatchers.withText(TESTING_DATA)));
  }
}

