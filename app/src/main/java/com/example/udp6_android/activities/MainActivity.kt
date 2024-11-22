package com.example.udp6_android.activities

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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.udp6_android.R
import com.example.udp6_android.adapter.MovieAdapter
import com.example.udp6_android.dao.MovieDao
import com.example.udp6_android.databinding.ActivityMainBinding
import com.example.udp6_android.model.Movie
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieList: List<Movie>
    private lateinit var adapter: MovieAdapter
    private lateinit var layoutManager: LayoutManager
    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private lateinit var movieDao: MovieDao
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
        this.title = getString(R.string.movies)
        movieDao = MovieDao()
        movieList = movieDao.findAll(this)
        layoutManager = LinearLayoutManager(this)
        binding.rvMovies.layoutManager = layoutManager
        this.adapter = MovieAdapter(movieList) { onSelectedItem(it) }
        binding.rvMovies.adapter = this.adapter
        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            result: ActivityResult ->
            if(result.resultCode == RESULT_OK) {
                val movieTitle = result.data?.extras?.getString("title").toString()
                val moviePosition = result.data?.extras?.getInt("moviePosition")
                moviePosition?.let {
                    adapter.getMovies()[it].title = movieTitle
                    movieDao.update(this, adapter.getMovies()[it])
                    this.adapter = MovieAdapter(movieList) { movie -> onSelectedItem(movie) }
                    binding.rvMovies.adapter = this.adapter
                }
            }
        }
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.itemAnimator = DefaultItemAnimator()
        setUpSwipeRefresh()
        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun filterList(newText: String?) {
        newText?.let { query ->
            val filteredMovies = movieList.filter { movie ->
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
                snackBarDialog(modifiedMovie)
            }
            1 -> {
                sendDataToEditableView(modifiedMovie, item)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun sendDataToEditableView(
        modifiedMovie: Movie,
        item: MenuItem
    ) {
        val intent = Intent(this, MovieEditable::class.java)
        intent.putExtra("title", modifiedMovie.title)
        intent.putExtra("imgResId", modifiedMovie.imgResId)
        intent.putExtra("moviePosition", item.groupId)
        intentLaunch.launch(intent)
    }

    private fun sendDataToDetail(
        movie: Movie
    ) {
        val intent = Intent(this, MovieDetail::class.java)
        intent.putExtra("movie", movie)
        intentLaunch.launch(intent)
    }

    private fun removeOneMovie(modifiedMovie: Movie) {
        movieList = movieList.filter { it != modifiedMovie }
        movieDao.delete(this, modifiedMovie)
        adapter.updateList(movieList)
        emptyList = movieList.isEmpty()
    }

    private fun snackBarDialog(
        modifiedMovie: Movie,
    ) {
        val dialog =
            AlertDialog.Builder(this).setTitle("Delete ${modifiedMovie.title}")
                .setMessage("Are you sure you want to delete ${modifiedMovie.title}")
                .setNeutralButton(getString(R.string.close_dialog_option), null).setPositiveButton(
                    getString(R.string.accept_dialog_option)
                ) { _, _ ->
                    display("Deleted ${modifiedMovie.title}")
                    removeOneMovie(modifiedMovie)
                }.create()
        dialog.show()
    }

    private fun snackBarDialog() {
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

    fun onSelectedItem(movie: Movie) {
        sendDataToDetail(movie)
    }

    private fun setUpSwipeRefresh() {
        binding.lySwipe.setOnRefreshListener {
            movieList = movieDao.findAll(this)
            adapter.updateList(movieList)
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
        movieDao.deleteAll(this)
        movieList = emptyList()
        adapter.updateList(movieList)
    }

    private fun loadMovies() {
        movieList = movieDao.findAll(this)
        adapter.updateList(movieList)
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}