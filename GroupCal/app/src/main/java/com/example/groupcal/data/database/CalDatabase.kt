package com.example.groupcal.data.database

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.data.database.dao.GroupDAO
import com.example.groupcal.data.database.dao.UserDAO
import com.example.groupcal.data.database.databaseModels.Event
import com.example.groupcal.data.database.databaseModels.Group
import com.example.groupcal.data.database.databaseModels.User
import com.example.groupcal.util.Converters

/**
 * The database used to store [User], [Group] end [Event] coming from the backend
 * This database uses [RoomDatabase]
 */
@Database(entities = [User::class, Group::class, Event::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CalDatabase : RoomDatabase() {

    /**
     * Defines the DAO used for CRUD operations on a [User]
     */
    abstract fun UserDAO(): UserDAO

    /**
     * Defines the DAO used for CRUD operations on a [Group]
     */
    abstract fun GroupDAO(): GroupDAO

    /**
     * Defines the DAO used for CRUD operations on an [Event]
     */
    abstract fun EventDAO(): EventDAO
}