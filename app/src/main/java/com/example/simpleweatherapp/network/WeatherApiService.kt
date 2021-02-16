package com.example.simpleweatherapp.network

import com.example.simpleweatherapp.models.WeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "4e850a559400f414b6ce0bfcd82d9827"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface WeatherApiService{
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ) : WeatherResponse
}

object WeatherApi{
    val retrofitService : WeatherApiService by lazy{
        retrofit.create(WeatherApiService::class.java)
    }
}