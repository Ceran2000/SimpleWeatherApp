package com.example.simpleweatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.databinding.CurrentWeatherFragmentBinding
import com.google.android.gms.location.*

class CurrentWeatherFragment : Fragment(), FindPlaceDialogFragment.FindPlaceListener {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder
    private val PERMISSION_ID = 44

    //TODO: button do lokalizacji, design, ładowanie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<CurrentWeatherFragmentBinding>(inflater, R.layout.current_weather_fragment, container, false)
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.findPlaceFab.setOnClickListener {
            openFindPlaceDialog()
        }

        viewModel.place.observe(viewLifecycleOwner, Observer {
            viewModel.getCurrentWeather()
        })

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()

        geocoder = Geocoder(context)


        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Log.i("LOCATION:", "IS NULL")
                        requestNewLocationData()
                    } else {
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)?.let {
                            Log.i("LOCATION:", it[0].locality)
                            viewModel.place.postValue(it[0].locality)
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)?.let {
                Log.i("LOCATION:", it[0].locality)
                viewModel.place.postValue(it[0].locality)
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun openFindPlaceDialog() {
        val findPlaceDialogFragment = FindPlaceDialogFragment()
        findPlaceDialogFragment.setTargetFragment(this, 1)
        findPlaceDialogFragment.show(parentFragmentManager, "findPlaceDialog")
    }

    override fun setPlace(place: String) {
        viewModel.place.value = place
    }

}