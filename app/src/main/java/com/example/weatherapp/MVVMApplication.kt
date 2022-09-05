package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.MyApi
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.repositories.LocRepository
import com.example.weatherapp.ui.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MVVMApplication))

        bind() from singleton { MyApi() }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { LocRepository(instance()) }
        bind() from provider { WeatherViewModelFactory(instance(), instance()) }

    }

}