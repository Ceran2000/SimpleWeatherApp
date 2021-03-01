package com.example.simpleweatherapp.ui


import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.simpleweatherapp.database.getDatabase
import com.example.simpleweatherapp.network.models.WeatherResponse
import com.example.simpleweatherapp.network.WeatherApiService
import com.example.simpleweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch


class CurrentWeatherViewModel(application: Application) : AndroidViewModel(application) {

    val place = MutableLiveData<String>()

    private val weatherRepository = WeatherRepository(getDatabase(application))

    val weather = weatherRepository.weather

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository(){
        viewModelScope.launch {
            try{
                weatherRepository.refreshWeather(place.value.toString())
            } catch (e: Exception){
                Log.i("FAILURE: ", e.printStackTrace().toString())
            }
        }
    }
}