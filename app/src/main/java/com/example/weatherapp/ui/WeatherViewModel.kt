package com.example.weatherapp.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.models.WeatherNext
import com.example.weatherapp.data.models.WeatherResponse
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.repositories.LocRepository
import com.example.weatherapp.util.Coroutines

class WeatherViewModel(private val repo: LocRepository, private val prefs: PreferenceProvider) : ViewModel(){

    var weatherListener: WeatherListener? = null

    var city: String? = null

    var temp: String? = null

    val cityLiveData = MutableLiveData<String>()

    val nextLiveData = MutableLiveData<WeatherNext>()

    val currLiveData = MutableLiveData<WeatherResponse>()

    fun onSearchButtonClick(view: View){
        weatherListener?.onStarted()
        closeSoftKeyboard(view.context, view)

        if(city.isNullOrEmpty()){
            weatherListener?.onFailure()
            return
        }

        //success
        Coroutines.main {
            val currResponse = repo.findWeather(city!!)
            if(currResponse.isSuccessful){
                weatherListener?.onSuccess()
                val lat = currResponse.body()?.coord?.lat
                val lon = currResponse.body()?.coord?.lon
                val nextResponse = repo.findNextWeather(lat!!, lon!!)
                cityLiveData.postValue(city)
                nextLiveData.postValue(nextResponse.body())
                currLiveData.postValue(currResponse.body())
            }else{
                weatherListener?.onFailure()
            }
        }
    }

    private fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }
}