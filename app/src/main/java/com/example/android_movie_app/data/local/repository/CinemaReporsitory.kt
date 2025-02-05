package com.example.android_movie_app.data.local.repository

import com.example.android_movie_app.data.local.dao.CinemaDAO
import com.example.android_movie_app.data.model.Cinema

class CinemaReporsitory(
    private val cinemaDAO: CinemaDAO
) {

    suspend fun findAll(): List<Cinema> = cinemaDAO.findAll()

    suspend fun findById(id: Int): Cinema? = cinemaDAO.findById(id)

    suspend fun save(cinema: Cinema) = cinemaDAO.save(cinema)

    suspend fun update(cinema: Cinema) = cinemaDAO.update(cinema)

    suspend fun deleteAll() = cinemaDAO.deleteAll()

    suspend fun deleteCinema(cinema: Cinema) = cinemaDAO.deleteCinema(cinema)

    suspend fun getCinemasByMovieId(movieId: Int): List<Cinema> = cinemaDAO.getCinemasByMovieId(movieId)

}