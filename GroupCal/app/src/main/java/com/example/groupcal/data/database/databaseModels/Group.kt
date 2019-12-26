package com.example.groupcal.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.groupcal.models.Group
import com.example.groupcal.models.User
import com.example.groupcal.util.Converters

@Entity(tableName = "group_table")
data class Group (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "",
    val color: String = "",
    val users: MutableList<User>


){
    fun toGroup() : Group {
        return com.example.groupcal.models.Group(
            id = this.id,
            name = this.name,
            color = this.color,
            members = this.users
        )
    }
}