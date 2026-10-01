/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.espresso.BasicSample

import androidx.test.ext.junit.rules.activityScenarioRule
import android.app.Activity
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.testing.espresso.BasicSample.Helper.getText
import com.example.android.testing.espresso.BasicSample.Helper.tap
import com.example.android.testing.espresso.BasicSample.Helper.typeText
import com.example.android.testing.espresso.BasicSample.Helper.waitForViewVisible
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * The kotlin equivalent to the ChangeTextBehaviorTest, that
 * showcases simple view matchers and actions like [ViewMatchers.withId],
 * [ViewActions.click] and [ViewActions.typeText], and ActivityScenarioRule
 *
 *
 * Note that there is no need to tell Espresso that a view is in a different [Activity].
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeTextBehaviorKtTest {

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    val FAVORITE_FOOD = "Khachapuri"
    val FIRST_MOVIE = "Inception"
    val SECOND_MOVIE = "Interstellar"

    @Test
    fun changeTextButton_showsEnteredFoodInSameActivity() {
        // Type favorite food, close keyboard, tap "Change text"
        onView(withId(R.id.editTextUserInput))
            .perform(ViewActions.typeText(FAVORITE_FOOD), closeSoftKeyboard())
        onView(withId(R.id.changeTextBt)).perform(click())

        // Verify the text above the input field
        onView(withId(R.id.textToBeChanged)).check(matches(withText(FAVORITE_FOOD)))
    }

    @Test
    fun changeTextThenOpenActivity_showsSecondMovieOnNextScreen() {
        // Type first movie, close keyboard, tap "Change text"
        onView(withId(R.id.editTextUserInput))
            .perform(ViewActions.typeText(FIRST_MOVIE), closeSoftKeyboard())
        onView(withId(R.id.changeTextBt)).perform(click())

        // Verify first movie is displayed above the input field
        onView(withId(R.id.textToBeChanged)).check(matches(withText(FIRST_MOVIE)))

        // Clear the field, type a different movie, close keyboard
        onView(withId(R.id.editTextUserInput)).perform(clearText())
        onView(withId(R.id.editTextUserInput))
            .perform(ViewActions.typeText(SECOND_MOVIE), closeSoftKeyboard())

        // Tap "Open activity and change text"
        onView(withId(R.id.activityChangeTextBtn)).perform(click())

        onView(withId(R.id.show_text_view)).check(matches(withText(SECOND_MOVIE)))
    }
}