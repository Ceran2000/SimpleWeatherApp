package com.example.simpleweatherapp

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageIcon")
fun bindImage(imgView: ImageView, imgIcon: String?){
    imgIcon?.let {
        val imgUrl = "https://openweathermap.org/img/wn/${imgIcon}@2x.png"
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}