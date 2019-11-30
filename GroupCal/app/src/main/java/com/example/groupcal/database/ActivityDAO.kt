package com.example.groupcal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface ActivityDAO {

    @Insert
    fun insert(activity: Activity)

    @Update
    fun update(activity: Activity)

    @Query("SELECT * from activity_table WHERE id = :key")
    fun get(key: Long): Activity?

    @Query("DELETE FROM activity_table")
    fun clear()

    @Query("SELECT * FROM activity_table ORDER BY name DESC")
    fun getAllActivities(): List<Activity>
    /*@Query("SELECT * FROM activity_table WHERE startDate = :date")
    fun getByDay(date: LocalDateTime): Activity?*/

}