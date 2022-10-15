package com.example.waterfit

import android.app.Application

class WaterfitApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}