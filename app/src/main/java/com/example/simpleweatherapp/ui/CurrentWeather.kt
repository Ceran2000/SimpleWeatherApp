package com.example.simpleweatherapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.databinding.CurrentWeatherFragmentBinding

class CurrentWeather : Fragment() {

    private val viewModel: CurrentWeatherViewModel by lazy {
        ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<CurrentWeatherFragmentBinding>(inflater, R.layout.current_weather_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        return binding.root
    }

}