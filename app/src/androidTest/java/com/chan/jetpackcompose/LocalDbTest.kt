package com.chan.jetpackcompose

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.chan.jetpackcompose.database.AppDatabase
import com.chan.jetpackcompose.database.User
import com.chan.jetpackcompose.database.UserDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */
@RunWith(AndroidJUnit4::class)
class LocalDbTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).allowMainThreadQueries().build()

        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb(){
        //db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() = runBlocking{
        val user = User("aher111@gmail.com", "Ahe11r","123456")
        val result  = userDao.insertUser(user)
        Log.e("CHANDRA", "$result")
        val data = userDao.getUser(1)
        assertEquals(data!!.userid, 1)
    }
}