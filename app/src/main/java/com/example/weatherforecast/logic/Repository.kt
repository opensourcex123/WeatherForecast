package com.example.weatherforecast.logic

import androidx.lifecycle.liveData
import com.example.weatherforecast.logic.model.Place
import com.example.weatherforecast.logic.network.WeatherForecastNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

// 仓库层的统一封装入口，单例类，在仓库层进行了一次线程转换
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherForecastNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)    // 将包装的结果发射出去
    }
}