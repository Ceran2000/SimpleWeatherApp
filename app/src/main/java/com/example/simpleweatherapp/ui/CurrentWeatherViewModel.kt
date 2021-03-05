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

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

/*    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown*/

    init {
        refreshDataFromRepository()
    }

    fun onNetworkErrorShown() {
        //_isNetworkErrorShown.value = false
        _eventNetworkError.value = false
    }

    fun refreshDataFromRepository(){
        viewModelScope.launch {
            try{
                weatherRepository.refreshWeather(place.value.toString())
                _eventNetworkError.value = false
                //_isNetworkErrorShown.value = false
            } catch (e: Exception){
                _eventNetworkError.value = true
                Log.i("NETWORK ERROR: ", e.printStackTrace().toString())
            }
        }
    }
}