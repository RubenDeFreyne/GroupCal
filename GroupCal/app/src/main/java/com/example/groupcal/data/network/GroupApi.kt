package com.example.groupcal.data.network

import com.example.groupcal.models.Group
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface GroupApi {

    @GET("groups")
    fun getGroups() : Deferred<List<Group>>
}