package com.example.groupcal.database.databaseModels

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
    val id: Long,
    val group_id: Long,
    val name: String
    /*val startDate: DateTime = DateTime.now(),
    val endDate: DateTime = DateTime.now()*/


)