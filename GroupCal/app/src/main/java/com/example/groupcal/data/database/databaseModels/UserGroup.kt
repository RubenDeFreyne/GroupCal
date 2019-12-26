package com.example.groupcal.data.database.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "user_group_table",
    primaryKeys = arrayOf("groupId","userId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = Group::class,
            parentColumns = arrayOf("databaseId"),
            childColumns = arrayOf("groupId")),
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("databaseId"),
            childColumns = arrayOf("userId"))
    )
)
data class UserGroup (

    val groupId: Long,

    @ColumnInfo(index = true)
    val userId: Long
)