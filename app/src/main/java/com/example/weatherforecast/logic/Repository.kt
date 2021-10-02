package com.example.weatherforecast.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.example.weatherforecast.logic.dao.PlaceDao
import com.example.weatherforecast.logic.model.Place
import com.example.weatherforecast.logic.model.Weather
import com.example.weatherforecast.logic.network.WeatherForecastNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

// 仓库层的统一封装入口，单例类，在仓库层进行了一次线程转换
object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherForecastNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    // 一次请求刷新天气，没有将实时天气与未来天气分开
    // 因为两个接口没有先后顺序，所以并发async函数执行，又因为async函数只能在协程作用域中使用，
    // 所以用coroutineScope函数创建了一个协程作用域
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                WeatherForecastNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                WeatherForecastNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    // 处理网络请求一次try，catch的高阶函数
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}