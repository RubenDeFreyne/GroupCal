package com.example.groupcal.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alamkanak.weekview.WeekViewEvent
import com.example.groupcal.models.Event
import java.text.SimpleDateFormat
import java.util.*

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

){
    fun toEvent(): WeekViewEvent<Event> {
        val startTime: Calendar = Calendar.getInstance()
        val endTime: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        startTime.setTime(sdf.parse(this.startTime))
        endTime.setTime(sdf.parse(this.endTime))

        return com.example.groupcal.models.Event(
            id = this.id,
            title = this.title,
            startTime = startTime,
            endTime = endTime,
            location = this.location,
            color = this.color,
            isAllDay = this.isAllDay,
            isCanceled = this.isCanceled
        ).toWeekViewEvent()
    }
}