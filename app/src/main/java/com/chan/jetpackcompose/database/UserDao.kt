package com.chan.jetpackcompose.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser():List<User>

    @Query("SELECT * FROM user WHERE email =:email AND password =:password")
    fun login(email: String, password: String): User

    @Query("SELECT * FROM user WHERE userid =:userID")
    fun getUser(userID:Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

}