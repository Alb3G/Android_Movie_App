package com.example.android_movie_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.android_movie_app.data.model.Movie

@Dao
interface MovieDAO {
    @Query("SELECT * From Movies ORDER BY id ASC;")
    suspend fun findAll(): List<Movie>

    @Query("SELECT * FROM Movies WHERE id = :id;")
    suspend fun findById(id: Int): Movie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(vararg movie: Movie)

    @Update
    suspend fun update(vararg movie: Movie)

    @Query("DELETE FROM Movies;")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie(vararg movie: Movie)
}