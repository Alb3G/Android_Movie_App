package com.example.android_movie_app.presentation.activities.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_movie_app.data.local.AppDataBase
import com.example.android_movie_app.data.local.repository.MovieRepository
import com.example.android_movie_app.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application
): AndroidViewModel(application) {

    private val movieRepository = MovieRepository(AppDataBase.getInstance(application).movieDAO())
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(movieRepository.findAll())
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.update(movie)
            loadMovies()
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
            loadMovies()
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.deleteAll()
        }
    }
}