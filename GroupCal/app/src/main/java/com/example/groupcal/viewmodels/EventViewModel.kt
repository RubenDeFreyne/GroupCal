package com.example.groupcal.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.EventRepository
import com.example.groupcal.models.Event
import java.text.SimpleDateFormat
import java.util.*

class EventViewModel : ViewModel() {
    private val eventRepository: EventRepository = EventRepository()
    val event = MutableLiveData<WeekViewDisplayable<Event>>()
    var title : String = ""
    var date : String = ""
    var time : String = ""
    var dateDetail : String = ""
    var location : String = ""


    fun getEvent (id : Long) {
        eventRepository.getEventsInRange(Calendar.getInstance().apply {
            var cal = Calendar.getInstance()
            set(Calendar.MONTH, cal.time.month - 1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)}, Calendar.getInstance().apply {
            val daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
            var cal = Calendar.getInstance()
            set(Calendar.MONTH, cal.time.month + 2)
            set(Calendar.DAY_OF_MONTH, daysInMonth)
            set(Calendar.HOUR_OF_DAY, 23)
        })
        Log.i("test", id.toString())

        event.value = eventRepository.getEvent(id)
        Log.i("test", eventRepository.getEvent(id).toString())


        //set title
        title = event.value?.toWeekViewEvent()?.title.toString()

        //set date
        val weekDay: String
        val dayFormat = SimpleDateFormat("EEEE", Locale.US)
        weekDay = dayFormat.format(event.value!!.toWeekViewEvent().startTime.time)

        var fmt = Formatter()
        val cal = Calendar.getInstance().apply {
            set(Calendar.MONTH, event.value!!.toWeekViewEvent().startTime.time.month)
        }
        fmt = Formatter()
        fmt.format("%tB", cal)

        date = "" + weekDay + " - " + fmt.toString() + " " + event.value!!.toWeekViewEvent().startTime.time.date.toString()

        //set time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
        time = "" + timeFormat.format(event.value!!.toWeekViewEvent().startTime.time) +  " - " + timeFormat.format(event.value!!.toWeekViewEvent().endTime.time)

        //set datedetail
        val dateDetailFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        dateDetail = dateDetailFormat.format(event.value!!.toWeekViewEvent().startTime.time)

        //set location
        location = event.value!!.toWeekViewEvent().location.toString()


    }
}