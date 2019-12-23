package com.example.groupcal.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.EventRepository
import com.example.groupcal.models.Event
import java.text.SimpleDateFormat
import java.util.*


class AddEventViewModel(val repo : EventRepository) : ViewModel() {

    var groupId = 0L
    lateinit var time: Date

    var title: String = ""
    var location: String = ""
    var color: String = ""

    lateinit var startTime : Date
    lateinit var endTime : Date

    fun getTimePreview() : String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
        return "" + timeFormat.format(startTime.time) +  " - " + timeFormat.format(endTime.time)
    }

    fun addEvent() : Boolean {

        if(title == "Event Title" || location == "Event Location" || color == "Pick a Color") {
            return false
        } else {
            val event = Event(
                id = 0L,
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

    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    fun checkTime(): Boolean {
        if (endTime.before(startTime))
            return false
        else return true
    }

}
