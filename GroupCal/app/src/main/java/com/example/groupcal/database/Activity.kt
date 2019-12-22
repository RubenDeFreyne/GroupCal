package com.example.groupcal.database

import androidx.room.ColumnInfo
import java.time.LocalDateTime
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.LocalDate;
@Entity(tableName = "activity_table", foreignKeys = arrayOf(
    ForeignKey(
    entity = Group::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("group_id"))
)
)
data class Activity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val group_id: Long,
    val name: String
    /*val startDate: DateTime = DateTime.now(),
    val endDate: DateTime = DateTime.now()*/


)