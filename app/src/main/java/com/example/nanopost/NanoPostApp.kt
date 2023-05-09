package com.example.nanopost

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NanoPostApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}