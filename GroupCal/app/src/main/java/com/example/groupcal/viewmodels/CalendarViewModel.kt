package com.example.groupcal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.EventRepository
import com.example.groupcal.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class CalendarViewModel(val repo : EventRepository) : ViewModel() {

    var groupId = ""
    val _events = MutableLiveData<List<WeekViewDisplayable<Event>>>()
    var events : LiveData<List<WeekViewDisplayable<Event>>> = _events

    private var repoJob = Job()
    private val coroutineScope = CoroutineScope(repoJob + Dispatchers.Main )

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

    fun fetchEvents(groupId : String){

        repo.groupId = groupId
        events = repo.getEvents(groupId)
    }
}
