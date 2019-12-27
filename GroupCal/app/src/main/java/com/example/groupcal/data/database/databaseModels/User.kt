package com.example.groupcal.data.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    val databaseId: Long = 0L,
    val backendId: String = "",
    val username: String = "test",
    val email: String = "test",
    val phone: String = "test"
)