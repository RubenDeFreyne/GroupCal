package com.example.groupcal.data.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.groupcal.models.Group
import com.example.groupcal.models.User

@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey(autoGenerate = false)
    val group_id: String = "",
    val name: String = "",
    val color: String = "",
    val users: MutableList<User>,
    val databaseId: Long = 0L


){
    fun toGroup() : Group {
        return com.example.groupcal.models.Group(
            backendId = this.group_id,
            name = this.name,
            color = this.color,
            members = this.users
        )
    }
}