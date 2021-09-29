package com.example.weatherforecast.logic.model

import com.google.gson.annotations.SerializedName

// data：数据类关键字，自动生成一些必要的函数，如toString与get，set
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
@SerializedName("formatted_address") val address: String)   // 添加注解使json字段与kotlin字段建立映射关系

data class Location(val lng: String, val lat: String) // 经纬度