package com.example.groupcal.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.repositories.EventRepository
import com.example.groupcal.models.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Formatter
import java.util.Calendar

/**
 * ViewModel for [AddEventFragment]
 *
 * @param repo The Repository for Events
 */
class AddEventViewModel(val repo: EventRepository) : ViewModel() {

    var groupId = ""
    lateinit var time: Date
    var title: String = ""
    var location: String = ""
    var color: String = ""
    lateinit var startTime: Date
    lateinit var endTime: Date

    /**
     * Get Text for timePreview textview
     */
    fun getTimePreview(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
        return "" + timeFormat.format(startTime.time) + " - " + timeFormat.format(endTime.time)
    }

    /**
     * Get Text for datePreview textview
     */
    fun getDatePreview(): String {
        var weekDay: String
        val dayFormat = SimpleDateFormat("EEEE", Locale.US)
        weekDay = dayFormat.format(time)

        var fmt = Formatter()
        var call = Calendar.getInstance().apply {
            set(Calendar.MONTH, time.month)
        }
        fmt = Formatter()
        fmt.format("%tB", time)

        return "" + weekDay + " - " + fmt.toString() + " " + time.date.toString()
    }

    /**
     * Add new event with properties filled in by user
     */
    fun addEvent(groupId: String): Boolean {

        if (title == "Event Title" || location == "Event Location" || color == "Pick a Color") {
            return false
        } else {
            val event = Event(
                id = "",
                title = title,
                location = location,
                color = Integer.parseInt(color),
                isAllDay = false,
                isCanceled = false,
                startTime = toCalendar(startTime),
                endTime = toCalendar(endTime)
            )
            Log.i("test", event.toString())
            repo.addEvent(event, groupId)
            return true
        }
    }

    /**
     * Date object to Calendar object
     */
    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    /**
     * Check if startTime is before endTime
     */
    fun checkTime(): Boolean {
        if (endTime.before(startTime))
            return false
        else return true
    }
}
