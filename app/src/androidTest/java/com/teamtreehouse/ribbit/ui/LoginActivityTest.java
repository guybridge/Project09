package com.teamtreehouse.ribbit.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.teamtreehouse.ribbit.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.EnumSet.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by guyb on 28/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest
{
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginWithNoPasswordShouldFail() throws Exception
    {
        // Arrange
        String username = "guyb";
        onView(withId(R.id.usernameField)).perform(typeText(username));

        // Act
        onView(withId(R.id.loginButton)).perform(click());

        // Assert - title id should still be there because activity should not go to main
        onView(withText(android.R.string.ok)).check(matches(notNullValue()));

    }

    @Test
    public void loginWithNoUsernameShouldFail() throws Exception
    {
        // Arrange
        String password = "testpass";
        onView(withId(R.id.passwordField)).perform(typeText(password));

        // Act
        onView(withId(R.id.loginButton)).perform(click());

        // Assert - title id should still be there because activity should not go to main
        onView(withText(android.R.string.ok)).check(matches(notNullValue()));

    }

    @Test
    public void loginWithNoUsernameOrPasswordShouldFail() throws Exception
    {
        // Arrange

        // Act
        onView(withId(R.id.loginButton)).perform(click());

        // Assert - title id should still be there because activity should not go to main
        onView(withText(android.R.string.ok)).check(matches(notNullValue()));

    }

    @Test
    public void loginWorks() throws Exception
    {
        // Arrange
        String username = "Daniel";
        String password = "TESTpass01";
        onView(withId(R.id.usernameField)).perform(typeText(username));
        onView(withId(R.id.passwordField)).perform(typeText(password));

        // Act
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(3000);

        // Assert
        onView(withId(R.id.pager)).check(matches(notNullValue()));

    }
}