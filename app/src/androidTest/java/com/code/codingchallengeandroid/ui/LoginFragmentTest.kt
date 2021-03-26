package com.code.codingchallengeandroid.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.code.codingchallengeandroid.R
import com.code.codingchallengeandroid.ToastMatcher
import com.code.codingchallengeandroid.getValue
import com.code.codingchallengeandroid.launchFragmentInHiltContainer
import com.code.codingchallengeandroid.roomdb.dao.UserDao
import com.code.codingchallengeandroid.roomdb.userdatabase.UserDatabase
import com.code.codingchallengeandroid.viewmodel.UserViewModel
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.text.IsEmptyString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class LoginFragmentTest{
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.userDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun emptyUserNameTest() {
        launchFragmentInHiltContainer<LoginFragment> {}
        onView(withId(R.id.buttonRegister)).perform(ViewActions.click())
        onView(withId(R.id.editTextUserName)).check(ViewAssertions.matches(withText(IsEmptyString.isEmptyString())))
    }

    @Test
    fun emptyPasswordTest() {
        launchFragmentInHiltContainer<LoginFragment> {}
        onView(withId(R.id.buttonRegister)).perform(ViewActions.click())
        onView(withId(R.id.editTextPassword)).check(ViewAssertions.matches(withText(IsEmptyString.isEmptyString())))
    }

    @Test
    fun clickCheck_userDataLogin(){
        launchFragmentInHiltContainer<LoginFragment> {}

        val userName = "abc"
        val password = "xyz"

        onView(withId(R.id.editTextUserName)).perform(typeText(userName))
        onView(withId(R.id.editTextPassword)).perform(typeText(password))
        closeSoftKeyboard()
        onView(withId(R.id.buttonLogin)).perform(ViewActions.click())

        onView(withText(R.string.toast_success)).inRoot(ToastMatcher())
            .check(matches(withText("Login Successful")))
    }
}