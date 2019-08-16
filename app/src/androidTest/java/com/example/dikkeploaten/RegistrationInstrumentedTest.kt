package com.example.dikkeploaten

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.dikkeploaten.Activities.MainActivity
import com.example.dikkeploaten.Activities.RegistrationActivity
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RegistrationInstrumentedTest {

    @get:Rule
    val registrationActivity = IntentsTestRule<RegistrationActivity>(RegistrationActivity::class.java)

    @Before
    fun init() {
        registrationActivity.getActivity()
            .getSupportFragmentManager().beginTransaction()
    }


    @Test
    fun goToRegistration_checkIfEverthingIsDisplayed() {
        Espresso.onView(withId(R.id.username)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.email)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.password)).check(matches(isDisplayed()))
    }

    @Test
    fun goToRegistration_SignUpWithValidInformation() {
        Espresso.onView(withId(R.id.username)).perform(typeText("Adrien")).perform(closeSoftKeyboard())
        Espresso.onView(withId(R.id.email)).perform(typeText("a@gmail.com")).perform(closeSoftKeyboard())
        Espresso.onView(withId(R.id.password)).perform(typeText("Testy123")).perform(closeSoftKeyboard())

        Espresso.onView(withId(R.id.register)).perform(click())

        Thread.sleep(2000)
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun goToRegistration_SignUpWithExistedInformation() {
        Espresso.onView(withId(R.id.username)).perform(typeText("Victor")).perform(closeSoftKeyboard())
        Espresso.onView(withId(R.id.email)).perform(typeText("victor@gmail.com")).perform(closeSoftKeyboard())
        Espresso.onView(withId(R.id.password)).perform(typeText("Testy123")).perform(closeSoftKeyboard())

        Espresso.onView(withId(R.id.register)).perform(click())

        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withText("Registratie is mislukt! Wachtwoord bevat te weinig tekens of emailadres is reeds in gebruik."))
            .inRoot(RootMatchers.withDecorView(Matchers.not(registrationActivity.activity.getWindow().getDecorView())))
            .check(matches(isDisplayed()));

    }

}