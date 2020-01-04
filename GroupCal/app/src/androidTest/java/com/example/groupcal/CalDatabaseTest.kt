package com.example.groupcal

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.example.groupcal.data.database.*
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.data.database.dao.GroupDAO
import com.example.groupcal.data.database.dao.UserDAO
import com.example.groupcal.data.database.databaseModels.Event
import com.example.groupcal.data.database.databaseModels.Group
import com.example.groupcal.data.database.databaseModels.User
import kotlinx.coroutines.*
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


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CalDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.UserDAO()
        groupDao = db.GroupDAO()
        eventDao = db.EventDAO()
        db.clearAllTables()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
    }

    // USER TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() {
        val user = User()
        userDao.insert(user)
        val dbuser = userDao.getAllUsers().blockingGet().last()
        assertEquals(user.username, dbuser.username)
    }

    // GROUP TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetGroup() {
        val group = Group(users = mutableListOf(), group_id = "group")
        groupDao.insert(group)
        assertEquals(groupDao.getRowCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGroupWithUsers() {

        val users: MutableList<com.example.groupcal.models.User> = mutableListOf()
        users += com.example.groupcal.models.User("user1")
        users += com.example.groupcal.models.User("user2")
        val group = Group(users = users, group_id = "usersgroup")
        groupDao.insert(group)
        val dbgroup = groupDao.get("usersgroup").blockingGet()
        assertEquals(2, dbgroup!!.users.size)
    }

    // EVENT TESTS
    @Test
    @Throws(Exception::class)
    fun insertAndGetEvent() {
        GlobalScope.launch(Dispatchers.Main) {
            val group = Group(users = mutableListOf(), group_id= "testid")
            groupDao.insert(group)
            val event = Event(group_id = "testid")
            eventDao.insert(event)
            eventDao.getAllEvents().observeOnce { assertEquals("testid", it.first().group_id) }
        }
    }
}

class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)
    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}
