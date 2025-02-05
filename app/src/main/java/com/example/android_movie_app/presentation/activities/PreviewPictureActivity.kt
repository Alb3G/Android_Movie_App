package com.example.android_movie_app.presentation.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.android_movie_app.R
import com.example.android_movie_app.data.local.AppDataBase
import com.example.android_movie_app.data.local.repository.MovieRepository
import com.example.android_movie_app.databinding.PreviewPictureActivityBinding
import kotlinx.coroutines.launch

class PreviewPictureActivity : AppCompatActivity() {

    private lateinit var binding: PreviewPictureActivityBinding
    private val movieRepository = MovieRepository(AppDataBase.getInstance(this).movieDAO())

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

        lifecycleScope.launch {
            val movie = movieRepository.findById(id)

            movie?.let {
                if (movie.uri.isNotEmpty())
                    binding.imageView.load(Uri.parse(movie.uri))
                else
                    Toast.makeText(
                        this@PreviewPictureActivity,
                        "${movie.title} does'nt have an associated snapshot",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

    }
}