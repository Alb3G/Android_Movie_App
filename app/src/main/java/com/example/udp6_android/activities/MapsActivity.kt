package com.example.udp6_android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.udp6_android.R
import com.example.udp6_android.dao.CinemaDAO
import com.example.udp6_android.databinding.ActivityMapsBinding
import com.example.udp6_android.model.Cinema
import com.example.udp6_android.model.Movie
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var cameraPosition: CameraPosition
    private val spain = LatLng(40.45049599256209, -4.1080792445398275)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true
        cameraPosition = CameraPosition.Builder().target(spain).zoom(5F).build()

        val movie = intent.getSerializableExtra("movie", Movie::class.java)
        this.title = movie?.title

        movie?.let {
            val cinemas: List<Cinema> = CinemaDAO().getCinemasByMovieID(this, it.id)
            cinemas.forEach { cinema ->
                val latLng = LatLng(cinema.latitude, cinema.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(cinema.name))
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}