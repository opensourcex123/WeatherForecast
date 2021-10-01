package com.example.weatherforecast.logic.model

// 将实时天气与未来几天天气数据封装成一个天气类
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily) {
}