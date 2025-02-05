package com.example.android_movie_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.example.android_movie_app.data.model.Cinema
import com.example.android_movie_app.data.model.Movie
import com.example.android_movie_app.data.model.MovieCinema

@Dao
interface DataDAO {
    @Insert
    fun insertMovies(data: List<Movie>)

    @Insert
    fun insertCinemas(data: List<Cinema>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieCinemas(data: List<MovieCinema>)

    @Transaction
    fun preloadData(movies: List<Movie>, cinemas: List<Cinema>, movieCinemas: List<MovieCinema>) {
        insertMovies(movies)
        insertCinemas(cinemas)
        insertMovieCinemas(movieCinemas)
    }
}