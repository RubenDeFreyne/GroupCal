package com.example.groupcal.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.EventRepository
import com.example.groupcal.models.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.text.SimpleDateFormat
import java.util.*

class EventViewModel(val repo: EventRepository) : ViewModel() {

    //Displayed events
    lateinit var event : WeekViewDisplayable<Event>

    //Event fields
    var title : String = ""
    var date : String = ""
    var time : String = ""
    var dateDetail : String = ""
    var location : String = ""

    //Coroutines






    fun getEvent (id : Long) {

        event = repo.getById(id)


        //set title
        title = event.toWeekViewEvent()?.title.toString()

        //set date
        val weekDay: String
        val dayFormat = SimpleDateFormat("EEEE", Locale.US)
        weekDay = dayFormat.format(event.toWeekViewEvent().startTime.time)

        var fmt = Formatter()
        val cal = Calendar.getInstance().apply {
            set(Calendar.MONTH, event.toWeekViewEvent().startTime.time.month)
        }
        fmt = Formatter()
        fmt.format("%tB", cal)

        date = "" + weekDay + " - " + fmt.toString() + " " + event.toWeekViewEvent().startTime.time.date.toString()

        //set time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
        time = "" + timeFormat.format(event.toWeekViewEvent().startTime.time) +  " - " + timeFormat.format(event.toWeekViewEvent().endTime.time)

        //set datedetail
        val dateDetailFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        dateDetail = dateDetailFormat.format(event.toWeekViewEvent().startTime.time)

        //set location
        location = event.toWeekViewEvent().location.toString()


    }
}