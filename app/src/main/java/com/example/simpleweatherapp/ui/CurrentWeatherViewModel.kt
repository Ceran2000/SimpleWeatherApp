package com.example.simpleweatherapp.ui


import android.util.Log
import androidx.lifecycle.*
import com.example.simpleweatherapp.models.WeatherResponse
import com.example.simpleweatherapp.network.WeatherApiService
import kotlinx.coroutines.launch


class CurrentWeatherViewModel() : ViewModel() {

    private val _response = MutableLiveData<WeatherResponse>()
    val response: LiveData<WeatherResponse>
        get() = _response

    var place = MutableLiveData<String>()

    init {
        getCurrentWeather()
    }


    fun getCurrentWeather() {
        place.value?.let {
            viewModelScope.launch {
                try {
                    val result =
                        WeatherApiService().getCurrentWeather(place.value.toString(), "metric")
                    _response.value = result
                } catch (e: Exception) {
                    Log.i("Failure: ", e.printStackTrace().toString())
                }
            }
        }

    }
}