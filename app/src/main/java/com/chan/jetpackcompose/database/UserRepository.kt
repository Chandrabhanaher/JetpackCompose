package com.chan.jetpackcompose.database

import android.util.Log
import androidx.lifecycle.LiveData


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */
class UserRepository (private val userDao: UserDao){

    val userData : LiveData<List<User>> = userDao.getUser()

    suspend fun addNewUser(user: User){
        userDao.insertUser(user)
    }
}