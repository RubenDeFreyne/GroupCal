package com.example.groupcal

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import androidx.room.Room
import com.example.groupcal.database.*
import com.example.groupcal.database.dao.EventDAO
import com.example.groupcal.database.dao.GroupDAO
import com.example.groupcal.database.dao.UserDAO
import com.example.groupcal.database.dao.UserGroupDAO
import com.example.groupcal.database.databaseModels.Event
import com.example.groupcal.database.databaseModels.Group
import com.example.groupcal.database.databaseModels.User
import com.example.groupcal.database.databaseModels.UserGroup

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
    private lateinit var eventDao: EventDAO
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
        eventDao = db.eventDAO
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
        val dbuser = userDao.getAllUsers().blockingGet().last()
        assertEquals(user.username, dbuser.username)
    }

    //GROUP TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetGroup() {
        val group = Group(users = mutableListOf())
        groupDao.insert(group)
        val dbgroup = groupDao.getAllGroups().blockingGet().last()
        assertEquals(group.name, dbgroup.name)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGroupWithUsers() {
        val users: MutableList<User> = mutableListOf()
        users += User()
        users += User()
        val group = Group(users = users)
        groupDao.insert(group)
        val dbgroup = groupDao.getAllGroups().blockingGet().last()
        assertEquals(2, dbgroup.users.size)
    }


    //USERGROUP TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetUserGroup() {
        val group = Group(users = mutableListOf())
        groupDao.insert(group)
        val user = User()
        userDao.insert(user)
        val dbuser = userDao.getAllUsers().blockingGet().last()
        val dbgroup = groupDao.getAllGroups().blockingGet().last()
        val usergroup = UserGroup(
            dbgroup.id,
            dbuser.id
        )
        userGroupDao.insert(usergroup)
        val dbusergroup = userGroupDao.getGroupsFromUser(dbuser.id).blockingGet().last()
        assertEquals(dbgroup, dbusergroup)
    }


    //EVENT TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetEvent() {
        val group = Group(users = mutableListOf())
        groupDao.insert(group)
        val dbGroup = groupDao.getAllGroups().blockingGet().last()
        val event= Event(group_id =  dbGroup.id)
        eventDao.insert(event)
        val dbEvent = eventDao.getAllEvents().blockingGet().last()
        assertEquals(dbGroup.id, dbEvent.group_id)
    }
}

