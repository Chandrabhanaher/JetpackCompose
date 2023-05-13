package com.chan.jetpackcompose.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */

class LocalDBViewModel(application: Application):ViewModel() {
    private val repository: UserRepository
    var user: LiveData<List<User>>? = null

    init {
        val db = DatabaseBuilder.getInstance(application).userDao()
        repository = UserRepository(db)
        user = repository.userData
    }

    fun addUser(user: User){
        viewModelScope.launch {
            repository.addNewUser(user)
        }
    }
}