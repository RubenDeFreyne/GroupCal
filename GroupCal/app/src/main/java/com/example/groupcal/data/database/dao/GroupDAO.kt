package com.example.groupcal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupcal.data.database.databaseModels.Group
import io.reactivex.Single

@Dao
interface GroupDAO {
    @Insert
    fun insert(group: Group)
    @Insert()
    fun insertMany(items: MutableList<Group>)


    @Update
    fun update(group: Group)

    @Query("SELECT * from group_table WHERE databaseId = :key")
    fun get(key: Long): Single<Group?>

    @Query("DELETE FROM group_table")
    fun clear()

    @Query("SELECT * FROM group_table ORDER BY name DESC")
    fun getAllGroups(): Single<List<Group>>

}