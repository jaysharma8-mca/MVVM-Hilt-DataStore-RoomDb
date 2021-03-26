package com.code.codingchallengeandroid.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.code.codingchallengeandroid.R
import com.code.codingchallengeandroid.ToastMatcher
import com.code.codingchallengeandroid.getOrAwaitValue
import com.code.codingchallengeandroid.launchFragmentInHiltContainer
import com.code.codingchallengeandroid.model.User
import com.code.codingchallengeandroid.roomdb.userdatabase.UserDatabase
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import com.code.codingchallengeandroid.userrepository.DefaultUserRepository
import com.code.codingchallengeandroid.viewmodel.UserViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.text.IsEmptyString.isEmptyString
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class RegistrationFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: UserViewModel
    private var context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        hiltRule.inject()
        val db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries().build()
        val dataSource = DefaultUserRepository(db.userDao(), UserPreferences(context))
        viewModel = UserViewModel(dataSource)
    }

    @Test
    fun emptyFullNameTest() {
        launchFragmentInHiltContainer<RegistrationFragment> {}
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.editTextName)).check(ViewAssertions.matches(withText(isEmptyString())))
    }

    @Test
    fun emptyUserNameTest() {
        launchFragmentInHiltContainer<RegistrationFragment> {}
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.editTextUserName)).check(ViewAssertions.matches(withText(isEmptyString())))
    }

    @Test
    fun emptyPasswordTest() {
        launchFragmentInHiltContainer<RegistrationFragment> {}
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.editTextPassword)).check(ViewAssertions.matches(withText(isEmptyString())))
    }

    @Test
    fun fullNameDigitTest(){
        launchFragmentInHiltContainer<RegistrationFragment> {}
        onView(withId(R.id.editTextName)).perform(typeText("1234"))
        onView(withId(R.id.buttonRegister)).perform(click())
    }

    @Test
    fun clickInsertIntoDb_userDataInsertedIntoDb(){
        launchFragmentInHiltContainer<RegistrationFragment> {}
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.editTextName)).perform(replaceText("Jay Sharma"), closeSoftKeyboard())
        onView(withId(R.id.editTextUserName)).perform(replaceText("jays"), closeSoftKeyboard())
        onView(withId(R.id.editTextPassword)).perform(replaceText("jays"), closeSoftKeyboard())
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.buttonRegister)).perform(click())

        viewModel.getUser()
        val result = viewModel.getUser.getOrAwaitValue().find {
            it.fullName == "Jay Sharma" && it.userName == "jays" && it.password == "jays"
        }
        assertThat(result != null).isTrue()
    }
}