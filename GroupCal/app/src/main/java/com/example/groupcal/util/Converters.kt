package com.example.groupcal.util

import androidx.room.TypeConverter
import com.example.groupcal.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

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

class CalAdapter {

    @ToJson
    fun fromCalendar(value: Calendar): String {
        return value.time.toString()
    }

    @FromJson
    fun toCalendar(value: String): Calendar {
        val time: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        time.setTime(sdf.parse(value))
        return time
    }

}