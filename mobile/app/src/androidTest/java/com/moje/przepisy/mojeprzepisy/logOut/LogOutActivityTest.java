package com.moje.przepisy.mojeprzepisy.logOut;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.moje.przepisy.mojeprzepisy.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogOutActivityTest {

  @Rule
  public ActivityTestRule<LogOutActivity> logoutActivityViewActivityTestRule =
      new ActivityTestRule<LogOutActivity>(LogOutActivity.class);

  @Test
  public void mainScreen_firstLoad(){
    onView(withId(R.id.logout_logo)).check(matches(isDisplayed()));
    onView(withId(R.id.logout_question_tv)).check(matches(isDisplayed()));
    onView(withId(R.id.logout_button_yes)).check(matches(isDisplayed()));
    onView(withId(R.id.logout_button_no)).check(matches(isDisplayed()));
  }
}
