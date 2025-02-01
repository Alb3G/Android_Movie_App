package com.example.android_movie_app.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.android_movie_app.R
import com.example.android_movie_app.dao.MovieDao
import com.example.android_movie_app.databinding.PreviewPictureActivityBinding

class PreviewPictureActivity : AppCompatActivity() {

    private lateinit var binding: PreviewPictureActivityBinding
    private val movieDao: MovieDao = MovieDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PreviewPictureActivityBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.title = "Movie Snapshot"
        val id = intent.extras!!.getInt("movieId")
        val movie = movieDao.getMovieById(this, id)
        if (movie.uri.isNotEmpty())
            binding.imageView.load(Uri.parse(movie.uri))
        else
            Toast.makeText(
                this,
                "${movie.title} does'nt have an associated snapshot",
                Toast.LENGTH_SHORT
            ).show()
    }
}