package com.example.groupcal.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.groupcal.database.databaseModels.Group
import com.example.groupcal.database.databaseModels.User

@Entity(tableName = "user_group_table",
    primaryKeys = arrayOf("groupId","userId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = Group::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId")),
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"))
    )
)
data class UserGroup (

    val groupId: Long,

    @ColumnInfo(index = true)
    val userId: Long
)