package com.example.groupcal.data.database.databaseModels

import androidx.room.*
import com.example.groupcal.models.Event
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class is used for converting an [Event] to an [Event] that can be stored in the [CalDatabase].
 *
 * @param title The title of the event
 * @param startTime The starting time of the event, formatted as a String
 * @param endTime The end time of the event, formatted as a String
 * @param location The location of the event
 * @param color The color of the event, formatted as a String
 * @param isAllDay Whether the event takes all day, currently not used, will be used in further implementation
 * @param isCanceled Whether the event is canceled, currently not used, will be used in further implementation
 * @param groupId The id of the [Group] the event belongs to
 * @param backendId The id generated in the backend
 *
 * @constructor Creates a new event with the given parameters
 */

@Entity(tableName = "event_table", indices = arrayOf(Index(value = ["backendId"], unique = true)), foreignKeys = arrayOf(
    ForeignKey(
    entity = Group::class,
    parentColumns = arrayOf("group_id"),
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
    val group_id: String,
    val backendId: String = ""

){
    /**
     * Converts an [Event] object from the database to an [Event] object and is used in the UI
     *
     * @return the [Event] used in the UI
     */

    fun toEvent(): Event{
        val startTime: Calendar = Calendar.getInstance()
        val endTime: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        startTime.setTime(sdf.parse(this.startTime))
        endTime.setTime(sdf.parse(this.endTime))

        return com.example.groupcal.models.Event(
            id = this.backendId,
            title = this.title,
            startTime = startTime,
            endTime = endTime,
            location = this.location,
            color = this.color,
            isAllDay = this.isAllDay,
            isCanceled = this.isCanceled,
            databaseId = this.id
        )
    }
}