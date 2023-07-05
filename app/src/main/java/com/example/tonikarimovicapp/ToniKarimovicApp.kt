package com.example.tonikarimovicapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.log.android.AndroidStreamLogger

@HiltAndroidApp
class ToniKarimovicApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidStreamLogger.installOnDebuggableApp(this)
    }
}