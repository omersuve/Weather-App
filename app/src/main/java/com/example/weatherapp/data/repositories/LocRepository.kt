package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.MyApi
import com.example.weatherapp.data.models.WeatherNext
import com.example.weatherapp.data.models.WeatherResponse
import retrofit2.Response

class LocRepository( private val api: MyApi) {
    suspend fun findWeather(city: String) : Response<WeatherResponse> {
        return api.searchLoc(city)
    }

    suspend fun findNextWeather(lat: Double, lon: Double) : Response<WeatherNext> {
        return api.searchWeather(lat, lon)
    }
}