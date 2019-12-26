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
    lateinit var event : LiveData<WeekViewDisplayable<Event>>

    //Event fields
    var title : String = ""
    var date : String = ""
    var time : String = ""
    var dateDetail : String = ""
    var location : String = ""

    //Coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    suspend fun getEventFromDb(id: Long) : LiveData<WeekViewDisplayable<Event>> {
        return withContext(Dispatchers.IO){
            repo.getById(id)
        }

    }

    fun getEvent (id : Long) {

        uiScope.launch {
            event = getEventFromDb(id)
        }


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