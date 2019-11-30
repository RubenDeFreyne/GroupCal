package com.example.groupcal.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room

@Database(entities = [User::class, Group::class, UserGroup::class, Activity::class], version = 1,  exportSchema = false)
abstract class CalDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO
    abstract val groupDAO: GroupDAO
    abstract val activityDAO: ActivityDAO
    abstract val userGroupDAO: UserGroupDAO

    companion object {

        @Volatile
        private var INSTANCE: CalDatabase? = null

        fun getInstance(context: Context): CalDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CalDatabase::class.java,
                        "group_cal_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}