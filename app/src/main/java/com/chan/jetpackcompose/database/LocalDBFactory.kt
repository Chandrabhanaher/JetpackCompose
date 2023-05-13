package com.chan.jetpackcompose.database

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by Chandrabhan Haribhau Aher on 13-05-2023.
 * chandrabhan99@gmail.com
 */
@Suppress("UNCHECKED_CAST")
class LocalDBFactory(private val application: Application): ViewModelProvider.Factory { //AndroidViewModelFactory(application)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

     return when(modelClass){
            LocalDBViewModel::class.java->{
                LocalDBViewModel(application)
            }else ->{ throw  IllegalArgumentException("Unknown class $modelClass")}
     } as T
    }
}