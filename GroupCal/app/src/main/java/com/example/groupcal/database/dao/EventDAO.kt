package com.example.groupcal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupcal.database.databaseModels.Event

@Dao
interface EventDAO {

    @Insert
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Query("SELECT * from event_table WHERE id = :key")
    fun get(key: Long): Event?

    @Query("DELETE FROM event_table")
    fun clear()

    @Query("SELECT * FROM event_table ORDER BY name DESC")
    fun getAllEvents(): List<Event>
    /*@Query("SELECT * FROM activity_table WHERE startDate = :date")
    fun getByDay(date: LocalDateTime): Activity?*/

    //TODO: Query voor activiteiten van groep

}