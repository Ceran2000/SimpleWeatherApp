package com.example.simpleweatherapp.ui


import android.util.Log
import androidx.lifecycle.*
import com.example.simpleweatherapp.models.WeatherResponse
import com.example.simpleweatherapp.network.WeatherApiService
import kotlinx.coroutines.launch

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class CurrentWeatherViewModel() : ViewModel() {

    private val _response = MutableLiveData<WeatherResponse>()
    val response: LiveData<WeatherResponse>
        get() = _response

    val place = MutableLiveData<String>()

    private val _status = MutableLiveData<WeatherApiStatus>()

    val status: LiveData<WeatherApiStatus>
        get() = _status

    init {
        getCurrentWeather()
    }


    fun getCurrentWeather() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                val result =
                    WeatherApiService()
                        .getCurrentWeather(place.value.toString(), "metric")
                _response.value = result
                _status.value = WeatherApiStatus.DONE
            } catch (e: Exception) {
                Log.i("Failure: ", e.printStackTrace().toString())
                _status.value = WeatherApiStatus.ERROR
            }
        }

    }
}