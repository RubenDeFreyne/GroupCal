package com.example.groupcal.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.groupcal.database.dao.EventDAO
import com.example.groupcal.database.dao.GroupDAO
import com.example.groupcal.database.dao.UserDAO
import com.example.groupcal.database.dao.UserGroupDAO
import com.example.groupcal.database.databaseModels.Event
import com.example.groupcal.database.databaseModels.Group
import com.example.groupcal.database.databaseModels.User
import com.example.groupcal.database.databaseModels.UserGroup
import com.example.groupcal.util.Converters

@Database(entities = [User::class, Group::class, UserGroup::class, Event::class], version = 1,  exportSchema = false)
@TypeConverters(Converters::class)
abstract class CalDatabase : RoomDatabase() {

    abstract fun UserDAO(): UserDAO
    abstract fun GroupDAO(): GroupDAO
    abstract fun EventDAO(): EventDAO
    abstract fun UserGroupDAO(): UserGroupDAO

    companion object {

        @Volatile
        private var INSTANCE: CalDatabase? = null

        fun getInstance(context: Context): CalDatabase {

                var instance = INSTANCE

                if (instance == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CalDatabase::class.java,
                        "group_cal_database"
                    )
                        .build()
                }
                return INSTANCE!!
            }
    }
}