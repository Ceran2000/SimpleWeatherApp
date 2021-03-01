package com.example.simpleweatherapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WeatherDao{

    @Query("select * from current_weather")
    fun getWeather(): LiveData<DatabaseWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: DatabaseWeather)
}

@Database(entities = [DatabaseWeather::class], version = 1)
abstract class WeatherDatabase: RoomDatabase(){
    abstract val weatherDao: WeatherDao
}

private lateinit var INSTANCE: WeatherDatabase

fun getDatabase(context: Context): WeatherDatabase{
    synchronized(WeatherDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            WeatherDatabase::class.java, "weather_db").build()
        }
    }
    return INSTANCE
}