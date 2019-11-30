package com.example.groupcal.database

import androidx.room.ColumnInfo
import java.time.LocalDateTime
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.LocalDate;
@Entity(tableName = "activity_table")
data class Activity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val name: String
    /*val startDate: DateTime = DateTime.now(),
    val endDate: DateTime = DateTime.now()*/


)