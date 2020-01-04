package com.example.groupcal.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alamkanak.weekview.WeekViewDisplayable
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.data.network.EventApi
import com.example.groupcal.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Repository used for managing all operations related to [Event]
 * This class implements [BaseRepo]
 *
 * @param dao class used to communicate with [CalDatabase]
 * @param api class used to communicate with backend
 * @param context used to check internet connectivity
 * @param repo used for attaching the group when creating new event
 */
class EventRepository(val dao: EventDAO, val api: EventApi, context: Context, val groupRepo: GroupRepository) : BaseRepo(context) {

    /**
     * Defines the Coroutine Job used by the repo
     */
    private var repoJob = Job()

    /**
     * Defines the Coroutine Scope used by the repo
     */
    private val coroutineScope = CoroutineScope(repoJob + Dispatchers.Main)

    /**
     * get a specific [Event] object in the [CalDatabase]
     *
     * @param id The id of the event
     *
     * @return WeekViewDisplayable event from database
     */
    fun getById(id: Long): WeekViewDisplayable<Event> {
        return dao.get(id).blockingGet()!!.toEvent().toWeekViewEvent()
    }

    /**
     * Create a new [Event] in the backend through [EventApi] and save it in the database through [EventDAO].
     * This also converts the response from the backend to an [Event] object
     *
     * @param event The event that has to be created
     */
    fun addEvent(event: Event, id: String) {
        event.group = groupRepo.getById(id)
        coroutineScope.launch {
            var getEvent = api.addEvent(event)
            try {
                var apiEvent = getEvent.await()
                dao.insert(apiEvent.toDatabaseEvent(id))
            } catch (t: Throwable) {
            }
        }
    }

    /**
     * Get the [Event] objects from [EventApi] if there is an internet connection, otherwise from [EventDAO]
     * the events are requested from the backend stored in the database.
     *
     * @return observable list of [Event] objects
     */
    fun getEvents(id: String): LiveData<List<WeekViewDisplayable<Event>>> {
        if (isConnected()) {
            coroutineScope.launch {
                var getGroups = api.getEvents()
                val listResult = getGroups.await()
                var events = listResult.filter { l -> l.group.backendId == id }
                events.forEach { r -> dao.insert(r.toDatabaseEvent(id)) }
            }
        }
        return Transformations.map(dao.getEventsByGroup(id), { l -> l.map { g -> g.toEvent() } })
    }
}