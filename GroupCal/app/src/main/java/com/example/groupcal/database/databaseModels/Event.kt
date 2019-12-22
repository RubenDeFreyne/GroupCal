package com.example.groupcal.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "event_table", foreignKeys = arrayOf(
    ForeignKey(
    entity = Group::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("group_id"))
)
)
data class Event(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val location: String = "",
    val color: Int = 0,
    val isAllDay: Boolean = false,
    val isCanceled: Boolean = false,
    @ColumnInfo(index = true)
    val group_id: Long

)