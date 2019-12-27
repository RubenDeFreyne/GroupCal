package com.example.groupcal.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "_id")
    val BackendId: String,
    @field:Json(name = "email")
    val email: String = "",
    @field:Json(name = "username")
    val username: String = "",
    @field:Json(name = "phone")
    val phone: String = "",
    val databaseId: Long = 0L
)