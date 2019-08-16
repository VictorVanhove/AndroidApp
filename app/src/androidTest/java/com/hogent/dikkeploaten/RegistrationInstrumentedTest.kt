package com.hogent.dikkeploaten

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.hogent.dikkeploaten.activities.MainActivity
import com.hogent.dikkeploaten.activities.RegistrationActivity
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
        registrationActivity.activity
            .supportFragmentManager.beginTransaction()
    }


    @Test
    fun goToRegistration_checkIfEverthingIsDisplayed() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.email)).check(matches(isDisplayed()))
        onView(withId(R.id.password)).check(matches(isDisplayed()))
    }

    @Test
    fun goToRegistration_SignUpWithValidInformation() {
        onView(withId(R.id.username)).perform(typeText("Adrien")).perform(closeSoftKeyboard())
        onView(withId(R.id.email)).perform(typeText("a@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("Testy123")).perform(closeSoftKeyboard())

        onView(withId(R.id.register)).perform(click())

        Thread.sleep(2000)
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun goToRegistration_SignUpWithExistedInformation() {
        onView(withId(R.id.username)).perform(typeText("Victor")).perform(closeSoftKeyboard())
        onView(withId(R.id.email)).perform(typeText("victor@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("Testy123")).perform(closeSoftKeyboard())

        onView(withId(R.id.register)).perform(click())

        Thread.sleep(2000)
        onView(ViewMatchers.withText("Registratie is mislukt! Wachtwoord bevat te weinig tekens of emailadres is reeds in gebruik."))
            .inRoot(RootMatchers.withDecorView(Matchers.not(registrationActivity.activity.window.decorView)))
            .check(matches(isDisplayed()))

    }

}