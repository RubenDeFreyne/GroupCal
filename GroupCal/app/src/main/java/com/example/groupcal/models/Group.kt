package com.example.groupcal.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Group(
    @field:Json(name = "_id")
    val backendId: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "color")
    val color: String?,
    @field:Json(name = "members")
    val members: MutableList<User>,
    val databaseId: Long = 0L

){
    fun toDatabaseGroup(): com.example.groupcal.data.database.databaseModels.Group {
        return com.example.groupcal.data.database.databaseModels.Group(
            name = this.name,
            color = this.color!!,
            users = this.members
        )
    }
}