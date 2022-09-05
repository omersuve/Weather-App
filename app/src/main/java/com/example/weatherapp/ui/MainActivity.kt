package com.example.weatherapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.util.Utils
import com.example.weatherapp.util.hide
import com.example.weatherapp.util.show
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(), WeatherListener, KodeinAware {
    private lateinit var viewModel: WeatherViewModel
    override val kodein by kodein()
    private val factory : WeatherViewModelFactory by instance()
    val gson = Gson()
    lateinit var prefs: PreferenceProvider
    lateinit var temp: String
    val setType: Type = object : TypeToken<MutableList<String?>?>() {}.getType()
    lateinit var tempList:  MutableList<String>
    lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.weatherListener = this

        initObservers()

        prefs = PreferenceProvider(this)

        if(prefs.getListItems() == null)
            prefs.addItem("PREVS")

        temp = prefs.getListItems()!!

        tempList = gson.fromJson(temp, setType)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tempList)

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = arrayAdapter
    }

    private fun initObservers(){
        viewModel.cityLiveData.observe(this, Observer {
            tv_city.text = it
            prefs.addItem(it)
        })
        viewModel.currLiveData.observe(this, Observer {
            result.text = it.weather[0].description
            w_img.setImageResource(Utils.getImageId(it.weather[0].icon))
            tv_temp.text = (it.main.temp.toString() + " \u2103")
        })
        viewModel.nextLiveData.observe(this, Observer {
            recycler_view.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = MyAdapter(it.daily)
            }
        })
    }

    override fun onStarted(){
        progressBar.show()
    }

    override fun onSuccess() {
        progressBar.hide()
    }

    override fun onFailure() {
        progressBar.hide()
    }
}
