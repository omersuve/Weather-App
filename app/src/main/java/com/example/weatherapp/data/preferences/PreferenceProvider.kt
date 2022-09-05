package com.example.weatherapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val KEY = "key_saved_at4"

class PreferenceProvider(
        context: Context
){

    private val appContext = context.applicationContext

    private val preference : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun addItem(city: String) {
        val gson = Gson()
        var prevList: String? = getListItems()
        if (prevList == null){
            var tmp2 = mutableListOf<String>()
            tmp2.add(city)
            var toJson: String = gson.toJson(tmp2, ArrayList::class.java)
            prevList = toJson
            val setType: Type = object : TypeToken<List<String?>?>() {}.getType()
            val currList: List<String> = gson.fromJson(prevList, setType)
            saveListItems(currList)
         }
        else {
            val setType: Type = object : TypeToken<List<String?>?>() {}.getType()
            val currList: List<String> = gson.fromJson(prevList, setType)
            val tmp: ArrayList<String> = currList as ArrayList<String>
            tmp.add(city)
            saveListItems(tmp)
        }
    }

    private fun saveListItems(cityList: List<String>){
        val gson = Gson()
        val citySet = cityList.toSet()
        val obj: String = gson.toJson(citySet, Set::class.java)
        preference.edit().apply {
            putString(KEY, obj).apply()
        }
    }

    fun getListItems(): String? {
        return preference.getString(KEY,null)
    }
}