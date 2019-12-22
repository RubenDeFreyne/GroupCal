package com.example.groupcal.viewmodels

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddEventViewModel : ViewModel() {
    lateinit var time: Date

    lateinit var startTime : Date
    lateinit var endTime : Date

    fun getTimePreview() : String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
        return "" + timeFormat.format(startTime.time) +  " - " + timeFormat.format(endTime.time)
    }

}