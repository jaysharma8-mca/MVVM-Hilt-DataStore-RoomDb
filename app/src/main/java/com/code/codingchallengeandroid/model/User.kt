package com.code.codingchallengeandroid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @ColumnInfo(name = "fullName")
    var fullName: String,

    @ColumnInfo(name = "userName")
    var userName: String,

    @ColumnInfo(name = "password")
    var password: String
) {

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}