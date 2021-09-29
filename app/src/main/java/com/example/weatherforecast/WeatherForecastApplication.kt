package com.example.weatherforecast

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

// 全局获取context的类
class WeatherForecastApplication : Application() {
    companion object {
        const val TOKEN = "L4EaKtJdM5zhblbT"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}