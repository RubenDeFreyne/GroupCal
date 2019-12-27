package com.example.groupcal.models

import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class Event(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "startTime")
    val startTime: Calendar,
    @field:Json(name = "endTime")
    val endTime: Calendar,
    @field:Json(name = "location")
    val location: String,
    @field:Json(name = "color")
    val color: Int,
    @field:Json(name = "group")
    var group: Group = Group(),

    val databaseId: Long = 0L,


    val isAllDay: Boolean = false,
    val isCanceled: Boolean = false
) : WeekViewDisplayable<Event> {

    override fun toWeekViewEvent(): WeekViewEvent<Event> {
        // Build the styling of the event, for instance background color and strike-through
        val style = WeekViewEvent.Style.Builder()
            .setBackgroundColor(color)
            .setTextStrikeThrough(isCanceled)
            .build()

        // Build the WeekViewEvent via the Builder
        return WeekViewEvent.Builder(this)
            .setId(databaseId)
            .setTitle(title)
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setLocation(location)
            .setAllDay(isAllDay)
            .setStyle(style)
            .build()
    }
        fun toDatabaseEvent(groupId: String): com.example.groupcal.data.database.databaseModels.Event {
            val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
            return com.example.groupcal.data.database.databaseModels.Event(
                id = this.databaseId,
                title = this.title,
                startTime = this.startTime.time.toString(),
                endTime = this.endTime.time.toString(),
                location = this.location,
                color = this.color,
                isAllDay = this.isAllDay,
                isCanceled = this.isCanceled,
                group_id = groupId,
                backendId = this.id
            )
        }
    }


