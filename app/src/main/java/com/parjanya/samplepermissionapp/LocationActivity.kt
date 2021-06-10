package com.parjanya.samplepermissionapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parjanya.samplepermissionapp.base.IPermissionActivity

class LocationActivity : FragmentActivity(), IPermissionActivity, OnMapReadyCallback {

    override val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) onPermissionAllowed() else onPermissionDenied()
    }

    private lateinit var map: GoogleMap
    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        checkPerm(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    override fun onPermissionAllowed() {
        map.isMyLocationEnabled = true
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(location.latitude, location.longitude)
                    map.addMarker(MarkerOptions()
                        .position(latLng)
                        .title("Current Location")
                    )
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                }
            }
    }

    override fun onPermissionDenied() {
        "Need to provide LOCATION permission to show location in map".showAsToast(this)
    }
}