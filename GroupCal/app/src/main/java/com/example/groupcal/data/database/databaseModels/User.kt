package com.example.groupcal.data.database.databaseModels

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class is used for converting a [User] to a [User] that can be stored in the [CalDatabase].
 *
 * @param databaseId The id of the user, generated by the [AndroidDatabase]
 * @param backendId The id of the user, generated by the backend
 * @param username The name of the user
 * @param email The email address of the user
 * @param phone the phone number of the user, formatted as a String
 *
 * @constructor Creates a new event with the given parameters
 */
@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = true)
    val databaseId: Long = 0L,
    val backendId: String = "",
    val username: String = "test",
    val email: String = "test",
    val phone: String = "test"
)