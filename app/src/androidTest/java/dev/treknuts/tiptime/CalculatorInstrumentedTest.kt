package dev.treknuts.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CalculatorInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_fifteen_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.tip_amount)).check(matches(withText(containsString("$8.00"))))
    }

    @Test
    fun calculate_eighteen_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.option_eighteen_percent)).perform(click())

        onView(withId(R.id.tip_amount)).check(matches(withText(containsString("$9.00"))))
    }

    @Test
    fun calculate_twenty_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.option_twenty_percent)).perform(click())

        onView(withId(R.id.tip_amount)).check(matches(withText(containsString("$10.00"))))
    }
}