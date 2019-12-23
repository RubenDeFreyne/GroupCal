package com.example.groupcal

import android.app.Application
import com.example.groupcal.injection.appComponent
import org.koin.android.ext.android.startKoin

class GroupCal : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, appComponent)
    }
}