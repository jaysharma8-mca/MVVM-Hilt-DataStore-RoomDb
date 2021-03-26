package com.code.codingchallengeandroid.userrepository

import android.content.Context
import androidx.lifecycle.LiveData
import com.code.codingchallengeandroid.model.User
import com.code.codingchallengeandroid.roomdb.dao.UserDao
import com.code.codingchallengeandroid.roomdb.userdatabase.UserDatabase
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userDao: UserDao,
    private val preferences: UserPreferences
    ) {


    var userDatabase: UserDatabase? = null

    var user: LiveData<User>? = null

    private fun initializeDB(context: Context): UserDatabase {
        return UserDatabase.getDataSetUser(context)
    }


    fun insertData(context: Context, fullName: String, userName: String, password: String) {

        userDatabase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            val loginDetails = User(fullName, userName, password)
            userDatabase!!.userDao().insertData(loginDetails)
        }

    }

    fun getDetails(context: Context): LiveData<User> {

        userDatabase = initializeDB(context)
        user = userDatabase!!.userDao().getUserData()
        return userDao.getUserData()


    }

    fun getLoginDetails(context: Context, userName: String, password: String): LiveData<User>? {

        userDatabase = initializeDB(context)

        user = userDatabase!!.userDao().getUserDetails(userName, password)

        return user
    }

    suspend fun deleteUsers() = userDao.delete()

    suspend fun saveData(fullName: String, userName: String, password: String){
        preferences.saveData(fullName, userName, password)
    }

    suspend fun getUser() = userDao.getUser()
}