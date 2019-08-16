package com.hogent.dikkeploaten

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.hogent.dikkeploaten.Activities.LoginActivity
import com.hogent.dikkeploaten.Activities.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginInstrumentedTest {

    @get:Rule
    val loginActivity = IntentsTestRule<LoginActivity>(LoginActivity::class.java)

    @Before
    fun init() {
        loginActivity.getActivity()
            .getSupportFragmentManager().beginTransaction()
    }

    @Test
    fun clickSignUpButton_navigateToRegistrationActivity() {
        onView(withId(R.id.register)).perform(click())
        onView(withId(R.id.username)).check(matches(isDisplayed()))
    }

    @Test
    fun goToLogin_checkIfEverthingIsDisplayed() {
        onView(withId(R.id.email)).check(matches(isDisplayed()))
        onView(withId(R.id.password)).check(matches(isDisplayed()))
    }

    @Test
    fun goToLogin_SignInWithValidInformation() {
        onView(withId(R.id.email)).perform(typeText("test@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("Testy123")).perform(closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())

        Thread.sleep(2000)
        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun goToLogin_SignInWithInvalidInformation() {
        onView(withId(R.id.email)).perform(typeText("dlkjfsmldk@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("Testy123")).perform(closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())

        Thread.sleep(2000)
        onView(withText("Login is mislukt! Probeer opnieuw."))
            .inRoot(withDecorView(not(loginActivity.activity.getWindow().getDecorView())))
            .check(matches(isDisplayed()));

    }

}
