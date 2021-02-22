package com.example.simpleweatherapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.simpleweatherapp.ui.WeatherApiStatus

@BindingAdapter("imageIcon")
fun bindImage(imgView: ImageView, imgIcon: String?) {
    imgIcon?.let {
        val imgUrl = "https://openweathermap.org/img/wn/${imgIcon}@2x.png"
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("weatherApiStatus")
fun bindStatus(statusImageView: ImageView, status: WeatherApiStatus?){
    when(status){
        WeatherApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        WeatherApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        WeatherApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}