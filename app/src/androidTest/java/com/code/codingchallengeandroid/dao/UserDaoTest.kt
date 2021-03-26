package com.code.codingchallengeandroid.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.code.codingchallengeandroid.getOrAwaitValue
import com.code.codingchallengeandroid.getValue
import com.code.codingchallengeandroid.launchFragmentInHiltContainer
import com.code.codingchallengeandroid.model.User
import com.code.codingchallengeandroid.roomdb.dao.UserDao
import com.code.codingchallengeandroid.roomdb.userdatabase.UserDatabase
import com.code.codingchallengeandroid.ui.LoginFragment
import com.code.codingchallengeandroid.ui.RegistrationFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named


@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class UserDaoTest {

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
    fun insertUserDataTest() = runBlockingTest{
        val userData = User("name name","name","name")
        dao.insertData(userData)
        val userTest = getValue(dao.getUserData())
        assertThat(userData.fullName).isEqualTo(userTest.fullName)
    }

    @Test
    fun deleteUserDataTest() = runBlockingTest{
        val userData = User("name name","name","name")
        dao.insertData(userData)
        dao.delete()
        assertThat(dao.getUserCount()).isEqualTo(0)
    }

    @Test
    fun getUserDetailsTest() = runBlockingTest {
        val userData = User("name name","name","name")
        dao.insertData(userData)
        val specificUser = dao.getUserDetails("name", "name").getOrAwaitValue()
        assertThat(specificUser).isEqualTo(userData)
    }
}