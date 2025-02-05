package com.example.android_movie_app.presentation.activities.mapsView

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android_movie_app.R
import com.example.android_movie_app.data.model.Movie
import com.example.android_movie_app.databinding.ActivityMapsBinding
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
    private val viewModel: MapsViewModel by viewModels()
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

        val movie = intent.getParcelableExtra("movie", Movie::class.java)
        this.title = movie?.title ?: "No movie selected"

        movie?.let {
            viewModel.getCinemasByMovieId(it.id)
            viewModel.cinemas.observe(this) { cinemas ->
                cinemas.forEach { cinema ->
                    val latLng = LatLng(cinema.latitude, cinema.longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(cinema.name))
                }
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}