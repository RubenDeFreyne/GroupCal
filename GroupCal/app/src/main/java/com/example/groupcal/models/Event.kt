package com.example.groupcal.models

import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import java.text.SimpleDateFormat
import java.util.*

data class Event(
    val id: Long,
    val title: String,
    val startTime: Calendar,
    val endTime: Calendar,
    val location: String,
    val color: Int,
    val isAllDay: Boolean,
    val isCanceled: Boolean
) : WeekViewDisplayable<Event> {

    override fun toWeekViewEvent(): WeekViewEvent<Event> {
        // Build the styling of the event, for instance background color and strike-through
        val style = WeekViewEvent.Style.Builder()
            .setBackgroundColor(color)
            .setTextStrikeThrough(isCanceled)
            .build()

        // Build the WeekViewEvent via the Builder
        return WeekViewEvent.Builder(this)
            .setId(id)
            .setTitle(title)
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setLocation(location)
            .setAllDay(isAllDay)
            .setStyle(style)
            .build()
    }
        fun toDatabaseEvent(groupId: Long): com.example.groupcal.database.databaseModels.Event {
            val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
            return com.example.groupcal.database.databaseModels.Event(
                id = this.id,
                title = this.title,
                startTime = this.startTime.time.toString(),
                endTime = this.endTime.time.toString(),
                location = this.location,
                color = this.color,
                isAllDay = this.isAllDay,
                isCanceled = this.isCanceled,
                group_id = groupId
            )
        }
    }


