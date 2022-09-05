package com.example.weatherapp.data.models


import com.google.gson.annotations.SerializedName

data class WeatherNext(
        val current: Current,
        val daily: List<Daily>,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        @SerializedName("timezone_offset")
    val timezoneOffset: Int
)