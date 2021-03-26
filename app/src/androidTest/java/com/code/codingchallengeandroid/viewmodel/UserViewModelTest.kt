package com.code.codingchallengeandroid.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.code.codingchallengeandroid.roomdb.userdatabase.UserDatabase
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import com.code.codingchallengeandroid.userrepository.DefaultUserRepository
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserViewModel

    private var context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        val db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries().build()
        val dataSource = DefaultUserRepository(db.userDao(), UserPreferences(context))
        viewModel = UserViewModel(dataSource)
    }

    @Test
    fun testInsertDataViewModel(){
        viewModel.insertData(context, "jay sharma", "jays", "jays")
        val result = viewModel.getDetails(context)
        Truth.assertThat(result != null).isTrue()
    }

    @Test
    fun testGetLoginDetailsViewModel(){
        viewModel.insertData(context, "jay sharma", "jays", "jays")
        val result = viewModel.getLoginDetails(context,"jays","jays")
        Truth.assertThat(result).isEqualTo(viewModel.userDataLogin)
    }


    @Test
    fun testDeleteUserViewModel(){
        viewModel.insertData(context, "jay sharma", "jays", "jays")
        viewModel.deleteUsers()
        Truth.assertThat(viewModel.userDataLogin).isNull()

    }

}