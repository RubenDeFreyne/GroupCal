package com.example.groupcal.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val username: String = "test",
    val email: String = "test",
    val phone: String = "test"
)