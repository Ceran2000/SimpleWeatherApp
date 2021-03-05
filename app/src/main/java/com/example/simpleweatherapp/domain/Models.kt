package com.example.simpleweatherapp.domain

data class CurrentWeather(
    val name: String,
    val country: String,
    val lon: Double,
    val lat: Double,
    val description: String,
    val icon: String,
    val temp: Int,
    val tempFeelsLike: Int,
    val humidity: Int,
    val pressure: Int
)