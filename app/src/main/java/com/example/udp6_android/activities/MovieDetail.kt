package com.example.udp6_android.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.udp6_android.R
import com.example.udp6_android.databinding.ActivityMovieDetailBinding
import com.example.udp6_android.model.Movie

class MovieDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val titleTv = binding.movieTitle
        val movieImg = binding.moviePoster
        val descTv = binding.movieDescription
        val yearTv = binding.movieYear
        val durationTv = binding.movieDuration
        val countryTv = binding.movieCountry
        setElementsWithData(intent, titleTv, movieImg, descTv, yearTv, durationTv, countryTv)
    }

    private fun setElementsWithData(
        intent: Intent,
        titleTv: TextView,
        movieImg: ImageView,
        descTv: TextView,
        yearTv: TextView,
        durationTv: TextView,
        countryTv: TextView
    ) {
        val movie = intent.getSerializableExtra("movie", Movie::class.java)
        this.title = movie?.title
        titleTv.text = movie?.title
        movieImg.setImageResource(movie!!.imgResId)
        descTv.text = getString(R.string.movie_desc, movie.description)
        yearTv.text = getString(R.string.movie_year, movie.releaseYear)
        durationTv.text = getString(R.string.movie_duration, movie.duration)
        countryTv.text = getString(R.string.movie_country, movie.country)
    }
}