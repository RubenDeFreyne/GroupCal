package com.example.groupcal.models

import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.IgnoredOnParcel

@JsonClass(generateAdapter = true)
data class Group(
    @field:Json(name = "_id")
    val backendId: String = "",
    @field:Json(name = "name")
    val name: String = "",
    @field:Json(name = "color")
    val color: String? = "",
    @field:Json(name = "members")
    val members: MutableList<User> = mutableListOf()

){
    fun toDatabaseGroup(): com.example.groupcal.data.database.databaseModels.Group {
        return com.example.groupcal.data.database.databaseModels.Group(
            name = this.name,
            color = this.color!!,
            users = this.members,
            group_id = this.backendId
        )
    }
}