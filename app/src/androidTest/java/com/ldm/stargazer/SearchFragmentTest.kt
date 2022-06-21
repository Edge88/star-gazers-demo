package com.ldm.stargazer


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI Tests to check is search button will enables in right conditions
 */

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(StarGazerActivity::class.java)

    @Test
    fun searchButtonEnabledTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.et_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tl_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("android"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_repo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tl_repo),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("architecture-samples"), closeSoftKeyboard())

        val button = onView(
            allOf(
                withId(R.id.button), withText("SEARCH!"),
                withParent(withParent(withId(R.id.nav_host_fragment_main))),
                isDisplayed()
            )
        )
        button.check(matches(isEnabled()))

    }

    @Test
    fun searchButtonDisabledRepoSpacingTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.et_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tl_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("android"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_repo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tl_repo),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("architecture samples"), closeSoftKeyboard())

        val button = onView(
            allOf(
                withId(R.id.button), withText("SEARCH!"),
                withParent(withParent(withId(R.id.nav_host_fragment_main))),
                isDisplayed()
            )
        )
        button.check(matches(isNotEnabled()))

    }

    @Test
    fun searchButtonDisabledOwnerEmptyTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.et_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tl_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText(""), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_repo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tl_repo),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("architecture-samples"), closeSoftKeyboard())

        val button = onView(
            allOf(
                withId(R.id.button), withText("SEARCH!"),
                withParent(withParent(withId(R.id.nav_host_fragment_main))),
                isDisplayed()
            )
        )
        button.check(matches(isNotEnabled()))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
