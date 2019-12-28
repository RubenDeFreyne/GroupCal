package com.example.groupcal.data.network

import com.example.groupcal.models.Group
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * Interface for sending requests related to [Group] to backend. This done by Retrofit.
 *
 * This is injected by koin in all the classes that use it
 */
interface GroupApi {

    /**
     * Get a list of the groups saved in the backend
     *
     * @return Deferred list of groups in the backend
     */
    @GET("groups")
    fun getGroups() : Deferred<List<Group>>

    /**
     * Create a new group in the backend
     *
     * @param group The group that has to be created in the backend
     *
     * @return Deferred of the created group
     */
    @POST("groups")
    fun addGroup(@Body group: Group): Deferred<Group>


}