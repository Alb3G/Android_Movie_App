package com.example.android_movie_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.android_movie_app.data.model.Cinema

@Dao
interface CinemaDAO {
    @Query("SELECT * From Cinemas ORDER BY id ASC;")
    suspend fun findAll(): List<Cinema>

    @Query("SELECT * FROM Cinemas WHERE id = :id;")
    suspend fun findById(id: Int): Cinema?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(vararg cinema: Cinema)

    @Update
    suspend fun update(vararg cinema: Cinema)

    @Query("DELETE FROM Cinemas;")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCinema(vararg cinema: Cinema)

    @Query("SELECT * FROM Cinemas as c JOIN Movie_Cinema as mc on c.id = mc.cinemaId WHERE mc.movieId = :id;")
    suspend fun getCinemasByMovieId(id: Int): List<Cinema>
}