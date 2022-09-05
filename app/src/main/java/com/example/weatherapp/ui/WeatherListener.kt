package com.example.weatherapp.ui

interface WeatherListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure()
}