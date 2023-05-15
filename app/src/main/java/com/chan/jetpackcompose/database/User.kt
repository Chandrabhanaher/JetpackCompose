package com.chan.jetpackcompose.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */
@Entity(tableName = "user")
data class User(
    @ColumnInfo(name="email")
    val email: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "password")
    val password: String
){
    @PrimaryKey(autoGenerate = true)
    var userid: Int = 0
}
