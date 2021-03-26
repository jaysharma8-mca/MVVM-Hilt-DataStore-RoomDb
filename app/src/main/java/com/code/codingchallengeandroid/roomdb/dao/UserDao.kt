package com.code.codingchallengeandroid.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.code.codingchallengeandroid.model.User


@Dao //Data Access Object
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(user: User)

    @Query("DELETE FROM user")
    suspend fun delete()

    @Query("SELECT * FROM user")
    fun getUserData(): LiveData<User>

    @Query("SELECT * FROM user WHERE userName =:username AND password = :password")
    fun getUserDetails(username: String?, password: String?) : LiveData<User>

    @Query("SELECT count(*) FROM user")
    fun getUserCount(): Int

    @Query("SELECT * FROM user")
    fun getAllUserData(): LiveData<List<User>>

    @Query("SELECT * FROM user")
    suspend fun getUser(): List<User>


}