package com.example.groupcal.data.database.databaseModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.groupcal.models.Group
import com.example.groupcal.models.User

@Entity(tableName = "group_table")
data class Group(

    @PrimaryKey(autoGenerate = true)
    val databaseId: Long = 0L,
    val backendId: String = "",
    val name: String = "",
    val color: String = "",
    val users: MutableList<User>


){
    fun toGroup() : Group {
        return com.example.groupcal.models.Group(
            backendId = this.backendId,
            name = this.name,
            color = this.color,
            members = this.users
        )
    }
}