package com.example.simpleweatherapp.network.models

import com.example.simpleweatherapp.database.DatabaseWeather
import com.example.simpleweatherapp.domain.CurrentWeather


data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind?
)

fun WeatherResponse.asDatabaseModel(): DatabaseWeather {
    return DatabaseWeather(
        name = this.name,
        country = this.sys.country,
        lon = this.coord.lon,
        lat = this.coord.lat,
        description = this.weather[0].description,
        icon = this.weather[0].icon,
        temp = this.main.temp,
        tempFeelsLike = this.main.feels_like,
        humidity = this.main.humidity,
        pressure = this.main.pressure
    )
}