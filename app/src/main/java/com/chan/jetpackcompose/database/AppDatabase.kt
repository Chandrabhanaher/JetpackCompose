package com.chan.jetpackcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){

    abstract fun userDao(): UserDao
}