package com.example.groupcal.models

import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * This class is used to convert JSON data from the backend to [Event] objects
 *
 * @param id The id generated in the backend defined by '_id' in JSON data
 * @param title The title of the event defined by 'title' in JSON data
 * @param startTime The starting time of the event defined by 'startTime' in JSON data
 * @param endTime The end time of the event defined by 'endTime' in JSON data
 * @param location The location of the event defined by 'location' in JSON data
 * @param color The color of the event defined by 'color' in JSON data
 * @param group The group of the event defined by 'group' in JSON data
 * @param databaseId The id generated in the database
 * @param isAllDay Whether the event takes all day, currently not used, will be used in further implementation
 * @param isCanceled Whether the event is canceled, currently not used, will be used in further implementation
 *
 * @constructor Creates a new [Event] object with the given parameters
 */
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

    /**
     * Converts an [Event] object to a WeekViewDisplayable [Event] object and is used to display
     * events in the calendar
     *
     * @return the WeekViewDisplayable [Event] of the given [Event]
     */
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

    /**
     * Converts an [Event] object used in the UI to an [Event] object used in the database
     *
     * @param id The id of the group the event belongs to
     * @return The database [Event] of the given [Event]
     */
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