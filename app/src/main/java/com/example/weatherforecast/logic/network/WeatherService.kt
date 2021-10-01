package com.example.weatherforecast.logic.network

import com.example.weatherforecast.WeatherForecastApplication
import com.example.weatherforecast.logic.model.DailyResponse
import com.example.weatherforecast.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    // 获取实时天气，path注解标识经纬度
    @GET("v2.5/${WeatherForecastApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    // 获取未来天气，path注解标识经纬度
    @GET("v2.5/${WeatherForecastApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}