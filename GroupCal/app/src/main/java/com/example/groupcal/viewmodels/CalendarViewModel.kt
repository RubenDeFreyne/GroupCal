package com.example.groupcal.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.EventRepository
import com.example.groupcal.models.Event
import java.time.LocalDate
import java.util.*

class CalendarViewModel(val repo : EventRepository) : ViewModel() {

    var groupId = 0L
    val events = MutableLiveData<List<WeekViewDisplayable<Event>>>()

    val startDate : Calendar = Calendar.getInstance().apply {
        var cal = Calendar.getInstance()
        set(Calendar.MONTH, cal.time.month - 1)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
    }

    val endDate : Calendar = Calendar.getInstance().apply {
        val daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
        var cal = Calendar.getInstance()
        set(Calendar.MONTH, cal.time.month + 2)
        set(Calendar.DAY_OF_MONTH, daysInMonth)
        set(Calendar.HOUR_OF_DAY, 23)
    }

    var currentlyViewing : Calendar = Calendar.getInstance()

    fun fetchEvents(start: Calendar, end: Calendar) {
        repo.groupId = groupId
        events.value = repo.getEventsFromDb()
    }
}
