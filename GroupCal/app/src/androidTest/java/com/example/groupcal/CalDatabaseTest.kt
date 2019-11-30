package com.example.groupcal

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import androidx.room.Room
import com.example.groupcal.database.*

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalDatabaseTest {
    private lateinit var db: CalDatabase
    private lateinit var userDao: UserDAO
    private lateinit var groupDao: GroupDAO
    private lateinit var activityDao: ActivityDAO
    private lateinit var userGroupDao: UserGroupDAO


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CalDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.userDAO
        groupDao = db.groupDAO
        activityDao = db.activityDAO
        userGroupDao = db.userGroupDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //USER TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() {
        val user = User()
        userDao.insert(user)
        val dbuser = userDao.getAllUsers().last()
        assertEquals(user.username, dbuser.username)
    }

    //GROUP TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetGroup() {
        val group = Group()
        groupDao.insert(group)
        val dbgroup = groupDao.getAllGroups().last()
        assertEquals(group.name, dbgroup.name)
    }

    //USERGROUP TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetUserGroup() {
        val group = Group()
        groupDao.insert(group)
        val user = User()
        userDao.insert(user)
        val dbuser = userDao.getAllUsers().last()
        val dbgroup = groupDao.getAllGroups().last()
        val usergroup = UserGroup(dbgroup.id, dbuser.id)
        userGroupDao.insert(usergroup)
        val dbusergroup = userGroupDao.getGroupsFromUser(dbuser.id).last()
        assertEquals(dbgroup, dbusergroup)
    }


    //ACTIVITY TESTS
}

