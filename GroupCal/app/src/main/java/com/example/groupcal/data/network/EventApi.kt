package com.example.groupcal.data.network

import com.example.groupcal.models.Event
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface for sending requests related to [Event] to backend. This done by Retrofit.
 *
 * This is injected by koin in all the classes that use it
 */
interface EventApi {

    /**
     * Get a list of the events saved in the backend
     *
     * @return Deferred list of events in the backend
     */
    @GET("events")
    fun getEvents(): Deferred<List<Event>>

    /**
     * Create a new event in the backend
     *
     * @param event The event that has to be created in the backend
     *
     * @return Deferred of the created event
     */

    @POST("events")
    fun addEvent(@Body event: Event): Deferred<Event>
}