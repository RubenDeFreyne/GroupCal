package com.example.groupcal.data

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.data.network.EventApi
import com.example.groupcal.models.Event
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventRepository (val dao: EventDAO, val api: EventApi, val context: Context, val groupRepo: GroupRepository) {

    var groupId = ""
    val events = mutableListOf<WeekViewDisplayable<Event>>()
    val dbevents = mutableListOf<com.example.groupcal.data.database.databaseModels.Event>()

    private var repoJob = Job()
    private val coroutineScope = CoroutineScope(repoJob + Dispatchers.Main )



    fun getEventsFromApi() : Deferred<List<Event>> {
        return api.getEvents()
    }


    fun getById(id : Long) : WeekViewDisplayable<Event> {
        return dao.get(id).blockingGet()!!.toEvent().toWeekViewEvent()

    }

    fun addEvent (event : Event, id: String) {
        event.group = groupRepo.getById(id)
        coroutineScope.launch {
            var getEvent = api.addEvent(event)
            try {
                var dbevent = getEvent.await()
                dao.insert(dbevent.toDatabaseEvent(id))
            } catch (t: Throwable) {

            }
        }

    }

    fun getEvents(id: String): LiveData<List<WeekViewDisplayable<Event>>>{
        var _events: MutableLiveData<List<WeekViewDisplayable<Event>>> = MutableLiveData()
        var events: LiveData<List<WeekViewDisplayable<Event>>> = _events
        val groupList : List<Event>
        if( isConnected()){
            coroutineScope.launch {
                var getGroups = getEventsFromApi()

                    var listResult = getGroups.await()
                    _events.value = listResult
                    listResult.forEach { r -> dao.insert(r.toDatabaseEvent(id)) }
            }
        }
            return Transformations.map(dao.getEventsByGroup(id), {l -> l.map { g -> g.toEvent() }})

    }

    protected fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }


}