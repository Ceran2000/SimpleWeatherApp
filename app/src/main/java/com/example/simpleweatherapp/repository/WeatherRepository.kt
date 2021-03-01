package com.example.simpleweatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.simpleweatherapp.database.WeatherDatabase
import com.example.simpleweatherapp.database.asDomainModel
import com.example.simpleweatherapp.domain.CurrentWeather
import com.example.simpleweatherapp.network.WeatherApiService
import com.example.simpleweatherapp.network.WeatherNetwork
import com.example.simpleweatherapp.network.models.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val database: WeatherDatabase) {

    var weather: LiveData<CurrentWeather> = Transformations.map(database.weatherDao.getWeather()){
        it.asDomainModel()
    }

    suspend fun refreshWeather(city: String){
        withContext(Dispatchers.IO){
            val weather = WeatherNetwork.service.getCurrentWeather(city, "metric")
            database.weatherDao.insert(weather.asDatabaseModel())
        }
    }
}