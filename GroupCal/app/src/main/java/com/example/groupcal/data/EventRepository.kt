package com.example.groupcal.data

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.models.Event
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventRepository (val dao: EventDAO) {

    var groupId = 0L
    val events = mutableListOf<WeekViewDisplayable<Event>>()
    val dbevents = mutableListOf<com.example.groupcal.data.database.databaseModels.Event>()

    fun setEventsInRange(
        startDate: Calendar,
        endDate: Calendar
    ){
        val year = startDate.get(Calendar.YEAR)
        val month = startDate.get(Calendar.MONTH) + 1

        val idOffset = year + 10L * month

        dbevents += newEvent(
            id = idOffset + 1,
            year = year,
            month = month,
            dayOfMonth = 28,
            hour = 16,
            minute = 0,
            duration = 90,
            color = Color.parseColor("#808000"),
            title = "Swimming",
            location = "Beach"
        ).toDatabaseEvent(groupId = groupId)

        // Add multi-day event
        dbevents += newEvent(
            id = idOffset + 2,
            year = year,
            month = month,
            dayOfMonth = 27,
            hour = 20,
            minute = 0,
            duration = 5 * 60,
            color = Color.parseColor("#808000"),
            title = "Dinner",
            location = "mcDonalds"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 3,
            year = year,
            month = month,
            dayOfMonth = 28,
            hour = 9,
            minute = 30,
            duration = 60,
            color = Color.parseColor("#808000"),
            title = "Breakfast",
            location = "Hotel"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 4,
            year = year,
            month = month,
            dayOfMonth = 28,
            hour = 10,
            minute = 30,
            duration = 45,
            color = Color.parseColor("#808000"),
            title = "Breakfast",
            location = "mcDonalds"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 5,
            year = year,
            month = month,
            dayOfMonth = 28,
            hour = 12,
            minute = 30,
            duration = 2 * 60,
            color = Color.parseColor("#808000"),
            title = "Lunch",
            location = "Pizza Hut"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 6,
            year = year,
            month = month,
            dayOfMonth = 17,
            hour = 11,
            minute = 0,
            duration = 4 * 60,
            color = Color.parseColor("#808000"),
            title = "Hiking",
            location = "Dunes"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 7,
            year = year,
            month = month,
            dayOfMonth = 15,
            hour = 3,
            minute = 0,
            duration = 3 * 60,
            color = Color.parseColor("#808000"),
            isCanceled = true,
            title = "Party",
            location = "Beach Bar"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 8,
            year = year,
            month = month,
            dayOfMonth = 1,
            hour = 9,
            minute = 0,
            duration = 3 * 60,
            color = Color.parseColor("#808000"),
            title = "Brunch",
            location = "Hotel"
        ).toDatabaseEvent(groupId = groupId)

        dbevents += newEvent(
            id = idOffset + 9,
            year = year,
            month = month,
            dayOfMonth = startDate.getActualMaximum(Calendar.DAY_OF_MONTH),
            hour = 15,
            minute = 0,
            duration = 3 * 60,
            color = Color.parseColor("#808000"),
            title = "Tanning",
            location = "Beach"
        ).toDatabaseEvent(groupId = groupId)

        // All-day event
        dbevents += newEvent(
            id = idOffset + 11,
            year = year,
            month = month,
            dayOfMonth = 28,
            hour = 0,
            minute = 0,
            duration = 24 * 60,
            isAllDay = true,
            color = Color.parseColor("#808000"),
            title = "Day Off",
            location = "Hotel"
        ).toDatabaseEvent(groupId = groupId)

        // All-day event until 00:00 next day
        dbevents += newEvent(
            id = idOffset + 12,
            year = year,
            month = month,
            dayOfMonth = 14,
            hour = 0,
            minute = 0,
            duration = 10 * 60,
            isAllDay = true,
            color = Color.parseColor("#808000"),
            title = "Day Off",
            location = "Hotel"
        ).toDatabaseEvent(groupId = groupId)

        dao.insertMany(dbevents)
    }

    fun getEventsFromDb(groupId : Long) : MutableList<WeekViewDisplayable<Event>> {

        val databaseevents =  dao.getEventsByGroup(groupId).blockingGet()
        val events = mutableListOf<WeekViewDisplayable<Event>>()
        databaseevents.forEach { e ->  run {
            val event = e.toEvent()
            events += event
        }}
        return events

    }

    private fun newEvent(
        id: Long,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hour: Int,
        minute: Int,
        duration: Int,
        color: Int,
        isAllDay: Boolean = false,
        isCanceled: Boolean = false,
        title: String,
        location: String
    ): Event {
        val startTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endTime = startTime.clone() as Calendar
        endTime.add(Calendar.MINUTE, duration)
        Log.i("test", id.toString())
        return Event(id, title, startTime, endTime, location, color, isAllDay, isCanceled)
    }

    private fun buildEventTitle(time: Calendar): String {
        val sdf = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)
        val formattedDate = sdf.format(time.time)
        val hour = time.get(Calendar.HOUR_OF_DAY)
        val minute = time.get(Calendar.MINUTE)
        return String.format("🦄 Event of %02d:%02d %s", hour, minute, formattedDate)
    }

    fun getById(id : Long) : LiveData<WeekViewDisplayable<Event>> {
        val event : LiveData<WeekViewDisplayable<Event>> = Transformations.map(dao.get(id), {e -> e!!.toEvent()})
        return event
    }

    fun addEvent (event : Event, id: Long) {
        dao.insert(event.toDatabaseEvent(id))
    }


}