package com.example.weatherforecast.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.weatherforecast.WeatherForecastApplication
import com.example.weatherforecast.logic.model.Place
import com.google.gson.Gson

// 记录选中城市存储数据的SharedPreferences方法
object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = WeatherForecastApplication.context.
            getSharedPreferences("weather_forecast", Context.MODE_PRIVATE)
}