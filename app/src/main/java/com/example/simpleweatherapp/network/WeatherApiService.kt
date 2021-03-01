package com.example.simpleweatherapp.network

import com.example.simpleweatherapp.network.models.WeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "4e850a559400f414b6ce0bfcd82d9827"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String
    ): WeatherResponse
}

object WeatherNetwork{
    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service = retrofit.create(WeatherApiService::class.java)
}