package com.example.simpleweatherapp.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpleweatherapp.domain.CurrentWeather

const val WEATHER_ID = 0
//TODO: poprawic nazewnictwo
@Entity(tableName = "current_weather")
data class DatabaseWeather constructor(
    val name: String,
    val lon: Double,
    val lat: Double,
    val description: String,
    val icon: String,
    val temp: Double,
    val tempFeelsLike: Double,
    val humidity: Int,
    val pressure: Int
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_ID
}

fun DatabaseWeather.asDomainModel(): CurrentWeather{
    return CurrentWeather(
        name = this.name,
        lon = this.lon,
        lat = this.lat,
        description = this.description,
        icon = this.icon,
        temp = this.temp,
        tempFeelsLike = this.tempFeelsLike,
        humidity = this.humidity,
        pressure = this.pressure
    )
}