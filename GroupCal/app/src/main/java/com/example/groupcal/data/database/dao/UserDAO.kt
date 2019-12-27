package com.example.groupcal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupcal.data.database.databaseModels.User
import io.reactivex.Single

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user_table WHERE databaseId = :key")
    fun get(key: Long): Single<User?>

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("SELECT * FROM user_table ORDER BY username DESC")
    fun getAllUsers(): Single<List<User>>
}