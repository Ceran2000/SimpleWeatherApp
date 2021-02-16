package com.example.simpleweatherapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleweatherapp.models.WeatherResponse
import com.example.simpleweatherapp.network.API_KEY
import com.example.simpleweatherapp.network.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherViewModel : ViewModel() {

    private val _response = MutableLiveData<WeatherResponse>()
    val response: LiveData<WeatherResponse>
        get() = _response

    init {
        getCurrentWeather()
    }


    private fun getCurrentWeather() {
        viewModelScope.launch {
            try {
                val result = WeatherApi.retrofitService.getCurrentWeather("Warsaw", API_KEY, "metric")
                _response.value = result
            } catch (e: Exception) {
                Log.i("Failure: ", e.printStackTrace().toString())
            }
        }
    }
}