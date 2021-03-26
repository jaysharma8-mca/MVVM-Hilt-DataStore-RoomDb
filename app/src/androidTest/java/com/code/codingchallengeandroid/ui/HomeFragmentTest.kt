package com.code.codingchallengeandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.code.codingchallengeandroid.R
import com.code.codingchallengeandroid.ToastMatcher
import com.code.codingchallengeandroid.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun logoutTest(){
        launchFragmentInHiltContainer<HomeFragment> {}
        Espresso.onView(withId(R.id.buttonLogout)).perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.toast_login)).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.withText("User Logged Out")))
    }
}