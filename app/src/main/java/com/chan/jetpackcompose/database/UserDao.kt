package com.chan.jetpackcompose.database

import androidx.annotation.IntDef
import androidx.lifecycle.LiveData
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
    fun getUser():LiveData<List<User>>


    @Query("SELECT * FROM user WHERE userid =:userID")
    fun getUser(userID:Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

}