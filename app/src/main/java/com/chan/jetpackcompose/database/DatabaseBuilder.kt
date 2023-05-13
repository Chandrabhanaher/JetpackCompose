
package com.chan.jetpackcompose.database

import android.content.Context
import androidx.room.Room


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */
object DatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase?= null

    fun getInstance(context: Context):AppDatabase{
        val tempInstance = INSTANCE

        if(tempInstance != null){
           return tempInstance
        }
        synchronized(this){
            val instance = buildLocalDB(context)
            if(INSTANCE == null){
                INSTANCE = buildLocalDB(context)
                INSTANCE = instance
            }
            return instance
        }
    }

    private fun buildLocalDB(context: Context) =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "APP_DB").build()
}