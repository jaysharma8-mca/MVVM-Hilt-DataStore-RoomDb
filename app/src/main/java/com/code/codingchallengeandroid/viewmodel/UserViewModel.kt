package com.code.codingchallengeandroid.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.codingchallengeandroid.model.User
import com.code.codingchallengeandroid.userrepository.DefaultUserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(private val userRepository: DefaultUserRepository): ViewModel() {

    var userDataLogin: LiveData<User>? = null

    private val _getUser = MutableLiveData<List<User>>()
    val getUser: LiveData<List<User>>
        get() = _getUser

    fun insertData(context: Context,fullName: String, userName: String, password: String) {
        userRepository.insertData(context,fullName, userName, password)
    }


    fun getDetails(context: Context) : LiveData<User>? {
        userDataLogin = userRepository.getDetails(context)
        return userDataLogin
    }

    fun getLoginDetails(context: Context, userName: String, password: String) : LiveData<User>? {
        userDataLogin = userRepository.getLoginDetails(context, userName, password)
        return userDataLogin
    }

    fun deleteUsers() = viewModelScope.launch {
        userRepository.deleteUsers()
    }

    suspend fun saveData(fullName: String, userName: String, password: String) {
        userRepository.saveData(fullName, userName, password)
    }

    fun getUser() = viewModelScope.launch {
        _getUser.value = userRepository.getUser()
    }

}