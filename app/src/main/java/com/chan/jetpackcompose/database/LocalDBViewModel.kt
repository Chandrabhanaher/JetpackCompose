package com.chan.jetpackcompose.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.withContext


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */

class LocalDBViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    var userList: LiveData<List<User>>?= null
    var singleUser: LiveData<User>? = null

    init {
        val db = DatabaseBuilder.getInstance(application).userDao()
        repository = UserRepository(db)
        userList = repository.userData
        singleUser = repository.singleUser
    }

     fun getAllUsers() {
        repository.getAllUsers()
    }

    suspend fun addUser(user: User): Long =
        withContext(viewModelScope.coroutineContext) {
            repository.addNewUser(user)
        }

    fun userLogin(email: String, password: String) {
        repository.userLogin(email, password)
    }

}