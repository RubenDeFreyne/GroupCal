package com.example.groupcal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface GroupDAO {
    @Insert
    fun insert(group: Group)

    @Update
    fun update(group: Group)

    @Query("SELECT * from group_table WHERE id = :key")
    fun get(key: Long): Group?

    @Query("DELETE FROM group_table")
    fun clear()

    @Query("SELECT * FROM group_table ORDER BY name DESC")
    fun getAllGroups(): List<Group>

}