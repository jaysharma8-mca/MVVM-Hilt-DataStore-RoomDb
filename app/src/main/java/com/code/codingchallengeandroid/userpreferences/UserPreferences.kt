package com.code.codingchallengeandroid.userpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences  @Inject constructor(@ApplicationContext context: Context) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(name = "my_data_store")

    val userName: Flow<String?>
    get() = dataStore.data.map {preferences ->
        preferences[FULL_NAME]
        preferences[USER_NAME]
        preferences[PASSWORD]
    }

    companion object {
        private val FULL_NAME = stringPreferencesKey("FullName")
        private val USER_NAME = stringPreferencesKey("UserName")
        private val PASSWORD = stringPreferencesKey("Password")
    }

    suspend fun saveData(fullName: String, userName: String, password: String){
        dataStore.edit { preferences ->
            preferences[FULL_NAME] = fullName
            preferences[USER_NAME] = userName
            preferences[PASSWORD] = password
        }
    }

    val userFullNameFlow: Flow<String> = dataStore.data.map {
        it[FULL_NAME] ?: ""
    }

    val userUserNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME] ?: ""
    }

    val userPasswordFlow: Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}