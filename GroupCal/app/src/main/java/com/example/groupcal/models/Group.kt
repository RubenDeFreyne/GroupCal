package com.example.groupcal.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class is used to convert JSON data from the backend to [Group] objects
 *
 * @param id The id generated in the backend defined by '_id' in JSON data
 * @param name The name of the group defined by 'name' in JSON data
 * @param color The color of the group defined by 'color' in JSON data
 * @param members The members of the group defined by 'members' in JSON data
 *
 * @constructor Creates a new [Group] object with the given parameters
 */
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

) {
    /**
     * Converts an [Group] object used in the UI to an [Group] object used in the database
     *
     * @return The database [Group] of the given [Group]
     */
    fun toDatabaseGroup(): com.example.groupcal.data.database.databaseModels.Group {
        return com.example.groupcal.data.database.databaseModels.Group(
            name = this.name,
            color = this.color!!,
            users = this.members,
            group_id = this.backendId
        )
    }
}