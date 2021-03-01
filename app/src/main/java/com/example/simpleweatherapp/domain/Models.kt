package com.example.simpleweatherapp.domain

data class CurrentWeather(
    val name: String,
    val lon: Double,
    val lat: Double,
    val description: String,
    val icon: String,
    val temp: Double,
    val tempFeelsLike: Double,
    val humidity: Int,
    val pressure: Int
)