package com.example.groupcal.data.network

import com.example.groupcal.models.Event
import com.example.groupcal.models.Group
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventApi {

    @GET("events")
    fun getEvents() : Deferred<List<Event>>

    @POST("events")
    fun addEvent(@Body event: Event): Deferred<Event>


}