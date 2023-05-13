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
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "userid")
    var userid: Int = 0,

    @ColumnInfo(name="email")
    var email: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "password")
    var password: String
)
