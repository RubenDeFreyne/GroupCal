package com.example.groupcal.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.groupcal.util.Converters

@Entity(tableName = "group_table")
data class Group (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "",
    val color: String = "",
    val users: MutableList<User>


)