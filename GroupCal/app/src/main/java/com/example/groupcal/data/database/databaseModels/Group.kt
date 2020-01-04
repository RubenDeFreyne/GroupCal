package com.example.groupcal.data.database.databaseModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.groupcal.models.Group
import com.example.groupcal.models.User

/**
 * This class is used for converting a [Group] to a [Group] that can be stored in the [CalDatabase]
 *
 *
 * @param group_id The id of the group generated in the backend
 * @param name The name of the group
 * @param color The color of the group, formatted as a String
 * @param users The list of [User] that are member of the group, currently not used, will be used in further implementation
 *
 * @constructor Creates a new group with the given params
 */
@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey(autoGenerate = false)
    val group_id: String = "",
    val name: String = "",
    val color: String = "",
    val users: MutableList<User>
) {
    /**
     * Converts a [Group] object from the database to a [Group] object and is used in the UI
     *
     * @return the [Group] used in the UI
     */
    fun toGroup(): Group {
        return com.example.groupcal.models.Group(
            backendId = this.group_id,
            name = this.name,
            color = this.color,
            members = this.users
        )
    }
}