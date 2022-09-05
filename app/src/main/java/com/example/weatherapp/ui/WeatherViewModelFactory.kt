package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.repositories.LocRepository

class WeatherViewModelFactory(
        private val repository: LocRepository,
        private val prefs: PreferenceProvider
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository, prefs) as T
    }
}