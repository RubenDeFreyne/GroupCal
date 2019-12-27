package com.example.groupcal.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groupcal.data.database.databaseModels.Group
import io.reactivex.Single

@Dao
interface GroupDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: Group)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(items: MutableList<Group>)


    @Update
    fun update(group: Group)

    @Query("SELECT * from group_table WHERE group_id = :key")
    fun get(key: String): Single<Group?>

    @Query("DELETE FROM group_table")
    fun clear()

    @Query("SELECT * FROM group_table ORDER BY name DESC")
    fun getAllGroups(): LiveData<List<Group>>

    @Query("select count(*) from group_table")
    fun getRowCount(): Int

}