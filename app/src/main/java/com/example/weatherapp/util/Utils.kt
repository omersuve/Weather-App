package com.example.weatherapp.util

import com.example.weatherapp.R

object Utils {

    fun getImageId(icon: String): Int {
        return when(icon) {
            "01d" -> R.drawable.ic_sun
            "02d" -> R.drawable.ic_cloud
            "03d" -> R.drawable.ic_cloud
            "04d" -> R.drawable.ic_overcast_clouds
            "10d" -> R.drawable.ic_rain
            "11d" -> R.drawable.ic_rain
            "12d" -> R.drawable.ic_rain
            "13d" -> R.drawable.ic_snowy
            else -> R.drawable.ic_cloud
        }
    }
}