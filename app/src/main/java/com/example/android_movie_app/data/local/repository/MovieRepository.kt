package com.example.android_movie_app.data.local.repository

import com.example.android_movie_app.data.local.dao.MovieDAO
import com.example.android_movie_app.data.model.Movie

class MovieRepository(
    private val movieDao: MovieDAO
)  {

    suspend fun findAll(): List<Movie> = movieDao.findAll()

    suspend fun findById(id: Int): Movie? = movieDao.findById(id)

    suspend fun save(movie: Movie) = movieDao.save(movie)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    suspend fun deleteAll() = movieDao.deleteAll()

    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)

}