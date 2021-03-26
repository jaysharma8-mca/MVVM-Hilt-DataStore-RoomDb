package com.code.codingchallengeandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.room.Room
import com.code.codingchallengeandroid.roomdb.dao.UserDao
import com.code.codingchallengeandroid.roomdb.userdatabase.UserDatabase
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import com.code.codingchallengeandroid.userrepository.DefaultUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton //Singleton as it provides only single instance of database object
    fun providesDatabase(@ApplicationContext context: Context) : UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "user")
            .build()

    @Provides
    fun providesUserDao(userDatabase: UserDatabase) : UserDao =
        userDatabase.userDao()

    @Provides
    fun providesUserRepository(userDao: UserDao, preferences: UserPreferences): DefaultUserRepository =
        DefaultUserRepository(userDao, preferences)

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.createDataStore("my_data_store")
}