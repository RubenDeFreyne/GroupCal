package com.example.groupcal.database.dao

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupcal.database.databaseModels.Event
import io.reactivex.Single

@Dao
interface EventDAO {

    @Insert
    fun insert(event: Event) : Long

    @Insert
    fun insertMany(items: MutableList<Event>)

    @Update
    fun update(event: Event)

    @Query("SELECT * from event_table WHERE id = :key")
    fun get(key: Long): Single<Event?>

    @Query("DELETE FROM event_table")
    fun clear()

    @Query("SELECT * FROM event_table ORDER BY title DESC")
    fun getAllEvents(): Single<List<Event>>

    @Query("SELECT * FROM event_table WHERE group_id =:id ORDER BY title DESC")
    fun getEventsByGroup(id: Long): Single<List<Event>>
    /*@Query("SELECT * FROM activity_table WHERE startDate = :date")
    fun getByDay(date: LocalDateTime): Activity?*/

    //TODO: Query voor activiteiten van groep

}