package com.example.groupcal.util

import androidx.room.TypeConverter
import com.example.groupcal.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromUserList(value: MutableList<User>): String {
        val gson = Gson()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toUserList(value: String): MutableList<User> {
        val gson = Gson()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(value, type)
    }
}