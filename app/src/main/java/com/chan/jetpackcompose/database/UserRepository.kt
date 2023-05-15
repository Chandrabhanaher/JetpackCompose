package com.chan.jetpackcompose.database

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */
class UserRepository (private val userDao: UserDao){

    val userData = MutableLiveData<List<User>>()
    val singleUser  =  MutableLiveData<User>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    suspend fun addNewUser(user: User):Long{
        return userDao.insertUser(user)
    }
    fun getAllUsers(){
        coroutineScope.launch {
            userData.postValue(userDao.getUser())
        }
    }

    fun userLogin(email: String, password: String){
        coroutineScope.launch {
            singleUser.postValue(userDao.login(email, password))
        }
    }
}