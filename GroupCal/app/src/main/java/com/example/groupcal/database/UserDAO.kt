package com.example.groupcal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user_table WHERE id = :key")
    fun get(key: Long): User?

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("SELECT * FROM user_table ORDER BY username DESC")
    fun getAllUsers(): List<User>
}