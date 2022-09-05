package com.example.weatherapp.data

import com.example.weatherapp.data.models.WeatherNext
import com.example.weatherapp.data.models.WeatherResponse
import com.example.weatherapp.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("weather")
    suspend fun searchLoc(
        @Query("q") city: String
    ) : Response<WeatherResponse>

    @GET("onecall")
    suspend fun searchWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double
    ) : Response<WeatherNext>

    companion object{
        operator fun invoke() : MyApi {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("appid", Constants.API_KEY)
                        .addQueryParameter("units", "metric")
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }
}