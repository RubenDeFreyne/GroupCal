package com.example.groupcal.data.network

import com.example.groupcal.models.Group
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface GroupApi {

    @GET("groups")
    fun getGroups() : Deferred<List<Group>>

    @POST("groups")
    fun addGroup(@Body group: Group): Deferred<Group>


}