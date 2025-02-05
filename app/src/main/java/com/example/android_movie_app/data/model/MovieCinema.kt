package com.example.android_movie_app.data.model

import androidx.room.Entity

@Entity(
    tableName = "Movie_Cinema",
    primaryKeys = ["movieId", "cinemaId"]
)
data class MovieCinema(
    val movieId: Int,
    val cinemaId: Int
)
