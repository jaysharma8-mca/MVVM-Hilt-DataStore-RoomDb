package com.code.codingchallengeandroid.utils

import java.util.regex.Pattern

object Validator {

    fun validateInput(fullName:String, userName: String, password: String) =
        !(fullName.isEmpty() || userName.isEmpty() || password.isEmpty())

    fun validateRegister(fullName:String) =
        fullName.isNotEmpty()

    fun validateLogin(fullName:String, userName: String, password: String) =
        !(fullName.isEmpty() || userName.isEmpty() || password.isEmpty())
}