package com.rafaltrzcinski.dribshots.shots.list

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.di.Injector
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShotsActivityTest {

    @JvmField @Rule val activityRule = ActivityTestRule<ShotsActivity>(ShotsActivity::class.java)

    private val idlingResource = object : IdlingResource {
        var callback: IdlingResource.ResourceCallback? = null
        val dispatcher = Injector.component.getOkHttpClient().dispatcher()

        override fun getName() = "ShotsIdlingResource"

        override fun isIdleNow(): Boolean {
            val idle = dispatcher.runningCallsCount() == 0
            if (idle) callback?.onTransitionToIdle()
            return idle
        }

        override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
            this.callback = callback
        }
    }

    @Before
    fun registerIdlingResources() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun shouldShowShotsActivity() {
        onView(withId(R.id.coordinator_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldToolbarBeVisibleAtFirstTime() {
        onView(withId(R.id.toolbar_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNoShotDetailsBeVisibleOnMainScreenBeforeClick() {
        onView(withId(R.id.fragment_frame)).check(matches(not(hasDescendant(withId(R.id.shot_details_image)))))
    }

    @Test
    fun shouldBePossibleToScrollDownOnShotsList() {
        onView(withId(R.id.shots_recycler)).perform(scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun shouldShotsListContainsItems() {
        onView(withId(R.id.shots_recycler)).perform(scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.shots_recycler)).check(matches(hasDescendant(withId(R.id.shot_card))))
    }

    @Test
    fun shouldOpenShotDetailsOnClick() {
        onView(withId(R.id.shots_recycler)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.shot_details_container)).check(matches(isDisplayed()))
    }
}