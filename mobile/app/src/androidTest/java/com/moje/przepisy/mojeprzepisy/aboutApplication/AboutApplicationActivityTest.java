package com.moje.przepisy.mojeprzepisy.aboutApplication;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.matcher.ViewMatchers.Visibility;
import android.support.test.rule.ActivityTestRule;
import com.moje.przepisy.mojeprzepisy.R;
import org.junit.Rule;
import org.junit.Test;

public class AboutApplicationActivityTest {
  @Rule
  public ActivityTestRule<AboutApplicationActivity> aboutAppActivityViewActivityTestRule =
      new ActivityTestRule<AboutApplicationActivity>(AboutApplicationActivity.class);

  @Test
  public void descriptionTitle_firstLoad(){
    onView(withId(R.id.app_description_title_my_card_view_layout)).check(matches(isDisplayed()));

    onView(withId(R.id.app_description_play_arrow)).check(matches(isDisplayed()));

    onView(withId(R.id.app_description_title_pl)).check(matches(isDisplayed()));
    onView(withId(R.id.app_description_title_pl)).
        check(matches(ViewMatchers.withText(R.string.app_description_title)));

    onView(withId(R.id.app_description_title_ang)).check(matches(isDisplayed()));
    onView(withId(R.id.app_description_title_ang)).
        check(matches(ViewMatchers.withText(R.string.app_description_title_ang)));
  }

  @Test
  public void descriptionText_firstLoad(){
    onView(withId(R.id.app_description_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));

    onView(withId(R.id.app_description_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));
  }

  @Test
  public void authorTitle_firstLoad(){
    onView(withId(R.id.app_author_title_my_card_view_layout)).check(matches(isDisplayed()));

    onView(withId(R.id.app_author_play_arrow)).check(matches(isDisplayed()));

    onView(withId(R.id.app_author_title_pl)).check(matches(isDisplayed()));
    onView(withId(R.id.app_author_title_pl)).
        check(matches(ViewMatchers.withText(R.string.app_author_title)));

    onView(withId(R.id.app_author_title_ang)).check(matches(isDisplayed()));
    onView(withId(R.id.app_author_title_ang)).
        check(matches(ViewMatchers.withText(R.string.app_author_title_ang)));
  }

  @Test
  public void authorText_firstLoad(){
    onView(withId(R.id.app_author_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));

    onView(withId(R.id.app_author_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));
  }

  @Test
  public void versionTitle_firstLoad(){
    onView(withId(R.id.app_version_title_my_card_view_layout)).check(matches(isDisplayed()));

    onView(withId(R.id.app_version_play_arrow)).check(matches(isDisplayed()));

    onView(withId(R.id.app_version_title_pl)).check(matches(isDisplayed()));
    onView(withId(R.id.app_version_title_pl)).
        check(matches(ViewMatchers.withText(R.string.app_version_title)));

    onView(withId(R.id.app_version_title_ang)).check(matches(isDisplayed()));
    onView(withId(R.id.app_version_title_ang)).
        check(matches(ViewMatchers.withText(R.string.app_version_title_ang)));
  }

  @Test
  public void versionText_firstLoad(){
    onView(withId(R.id.app_version_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));

    onView(withId(R.id.app_version_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));
  }

  @Test
  public void errorTitle_firstLoad(){
    onView(withId(R.id.app_error_title_my_card_view_layout)).check(matches(isDisplayed()));

    onView(withId(R.id.app_error_play_arrow)).check(matches(isDisplayed()));

    onView(withId(R.id.app_error_title_pl)).check(matches(isDisplayed()));
    onView(withId(R.id.app_error_title_pl)).
        check(matches(ViewMatchers.withText(R.string.app_error_title)));

    onView(withId(R.id.app_error_title_ang)).check(matches(isDisplayed()));
    onView(withId(R.id.app_error_title_ang)).
        check(matches(ViewMatchers.withText(R.string.app_error_title_ang)));
  }

  @Test
  public void errorText_firstLoad(){
    onView(withId(R.id.app_error_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));

    onView(withId(R.id.app_error_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.GONE)));
  }

  @Test
  public void appDescriptionTextChangeVisibility(){
    onView(withId(R.id.app_description_title_my_card_view_layout)).
        perform(click());
    onView(withId(R.id.app_description_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    onView(withId(R.id.app_description_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
  }

  @Test
  public void appAuthorTextChangeVisibility(){
    onView(withId(R.id.app_author_title_my_card_view_layout)).
        perform(click());
    onView(withId(R.id.app_author_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    onView(withId(R.id.app_author_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
  }

  @Test
  public void appVersionTextChangeVisibility(){
    onView(withId(R.id.app_version_title_my_card_view_layout)).
        perform(click());
    onView(withId(R.id.app_version_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    onView(withId(R.id.app_version_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
  }

  @Test
  public void appErrorTextChangeVisibility(){
    onView(withId(R.id.app_error_title_my_card_view_layout)).
        perform(click());
    onView(withId(R.id.app_error_text_pl_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    onView(withId(R.id.app_error_text_ang_my_card_view_layout)).
        check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
  }


}
