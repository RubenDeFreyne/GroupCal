package com.example.groupcal.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groupcal.data.database.databaseModels.Event
import io.reactivex.Single

@Dao
interface EventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(items: MutableList<Event>)

    @Update
    fun update(event: Event)

    @Query("SELECT * from event_table WHERE id =:key")
    fun get(key: Long): Single<Event?>

    @Query("DELETE FROM event_table")
    fun clear()

    @Query("SELECT * FROM event_table ORDER BY title ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event_table WHERE group_id =:id ORDER BY title DESC")
    fun getEventsByGroup(id: String): LiveData<List<Event>>

    @Query("select count(*) from event_table")
    fun getRowCount(): Int

}