package com.example.android_movie_app.presentation.activities.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.android_movie_app.R
import com.example.android_movie_app.data.model.Movie
import com.example.android_movie_app.databinding.ActivityMainBinding
import com.example.android_movie_app.presentation.activities.MovieDetailActivity
import com.example.android_movie_app.presentation.activities.MovieEditableActivity
import com.example.android_movie_app.presentation.activities.PictureActivity
import com.example.android_movie_app.presentation.activities.PreviewPictureActivity
import com.example.android_movie_app.presentation.activities.mapsView.MapsActivity
import com.example.android_movie_app.presentation.adapter.MovieAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var layoutManager: LayoutManager
    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private val viewModel: HomeViewModel by viewModels()
    private var emptyList: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupObservers()
        setupUI()
        setupActivityResultLauncher()
        setUpSwipeRefresh()

        viewModel.loadMovies()
    }

    private fun setupUI() {
        val movieList = viewModel.movies.value ?: emptyList()
        this.title = getString(R.string.movies)

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }

        adapter = MovieAdapter(movieList) { onSelectedItem(it) }
        binding.rvMovies.adapter = adapter

        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setupObservers() {
        viewModel.movies.observe(this) { movieList ->
            adapter.updateList(movieList)
        }
    }

    private fun setupActivityResultLauncher() {
        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val movieTitle = result.data?.extras?.getString("title").toString()
                val moviePosition = result.data?.extras?.getInt("moviePosition")

                moviePosition?.let {
                    val updatedMovie = adapter.getMovies()[it].copy(title = movieTitle)
                    viewModel.updateMovie(updatedMovie)
                }
            }
        }
    }

    private fun filterList(newText: String?) {
        newText?.let { query ->
            val movies = viewModel.movies.value ?: emptyList()
            val filteredMovies = movies.filter { movie ->
                movie.title.contains(query, ignoreCase = true)
            }
            if (filteredMovies.isEmpty()) {
                Toast.makeText(this, "The movie doesn't exist.", Toast.LENGTH_SHORT).show()
            }
            adapter.updateList(filteredMovies)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val modifiedMovie: Movie = adapter.getMovies()[item.groupId]

        when(item.itemId) {
            0 -> {
                deleteMovie(modifiedMovie)
            }
            1 -> {
                sendDataToEditableView(modifiedMovie, item)
            }
            2 -> {
                sendDataToDetail(modifiedMovie)
            }
            3 -> {
                val intent = Intent(this, PictureActivity::class.java)
                intent.putExtra("movie", modifiedMovie.title)
                intent.putExtra("id", modifiedMovie.id)
                this.startActivity(intent)
            }
            4 -> {
                val intent = Intent(this, PreviewPictureActivity::class.java)
                intent.putExtra("movieId", modifiedMovie.id)
                this.startActivity(intent)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun sendDataToEditableView(
        modifiedMovie: Movie,
        item: MenuItem
    ) {
        val intent = Intent(this, MovieEditableActivity::class.java)
        intent.putExtra("title", modifiedMovie.title)
        intent.putExtra("imgResId", modifiedMovie.imgResId)
        intent.putExtra("moviePosition", item.groupId)
        intentLaunch.launch(intent)
    }

    private fun sendDataToDetail(
        movie: Movie
    ) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie)
        intentLaunch.launch(intent)
    }

    private fun removeMovie(modifiedMovie: Movie) {
        var movieList = viewModel.movies.value ?: emptyList()
        movieList = movieList.filter { it != modifiedMovie }
        viewModel.deleteMovie(modifiedMovie)
        adapter.updateList(movieList)
        emptyList = movieList.isEmpty()
    }

    private fun deleteMovie(
        modifiedMovie: Movie,
    ) {
        val dialog =
            AlertDialog.Builder(this).setTitle("Delete ${modifiedMovie.title}")
                .setMessage("Are you sure you want to delete ${modifiedMovie.title}")
                .setNeutralButton(getString(R.string.close_dialog_option), null).setPositiveButton(
                    getString(R.string.accept_dialog_option)
                ) { _, _ ->
                    display("Deleted ${modifiedMovie.title}")
                    removeMovie(modifiedMovie)
                }.create()
        dialog.show()
    }

    private fun snackBarDialog() {
        val movieList = viewModel.movies.value ?: emptyList()
        val dialog =
            AlertDialog.Builder(this).setTitle("Eliminar All Movies")
                .setMessage(
                    "Are you sure that you want to delete all the movies?"
                )
                .setNeutralButton(getString(R.string.close_dialog_option), null).setPositiveButton(
                    getString(R.string.accept_dialog_option)
                ) { _, _ ->
                    display("Deleted ${movieList.size} movies")
                    clearMovies()
                }.create()
        dialog.show()
    }

    private fun onSelectedItem(movie: Movie) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("movie", movie)
        intentLaunch.launch(intent)
    }

    private fun setUpSwipeRefresh() {
        binding.lySwipe.setOnRefreshListener {
            viewModel.loadMovies()
            binding.lySwipe.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.search_movies)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_Movies -> {
                snackBarDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearMovies() {
        viewModel.deleteAll()
        adapter.updateList(emptyList())
    }

    private fun loadMovies() {
        val movieList = viewModel.movies.value ?: emptyList()
        viewModel.loadMovies()
        adapter.updateList(movieList)
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}