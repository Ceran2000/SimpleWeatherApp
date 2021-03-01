package com.example.simpleweatherapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrentWeatherViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrentWeatherViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}