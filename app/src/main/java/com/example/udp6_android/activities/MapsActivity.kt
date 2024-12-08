package com.example.udp6_android.activities

import android.os.Bundle
import android.util.Log
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true
        cameraPosition = CameraPosition.Builder().target(spain).zoom(5F).build()

        val movie = intent.getSerializableExtra("movie", Movie::class.java)
        this.title = movie?.title

        movie?.let {
            val cinemas: List<Cinema> = CinemaDAO().getCinemasByMovieID(this, it.id)
            Log.d("Cinemas Id By Movie", "Size: ${cinemas.size}")
            cinemas.forEach { cinema ->
                Log.d("Cinema Id", "Id: ${cinema.id}")
                // Revisar con Logs las coordenadas, pueden dar problemas a la hora de crear el marcador.
                val latLng = LatLng(cinema.latitude, cinema.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(cinema.name))
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}