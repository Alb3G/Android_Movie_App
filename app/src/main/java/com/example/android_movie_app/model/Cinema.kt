package com.example.android_movie_app.model

import java.io.Serializable

data class Cinema(
    val id: Int,
    val name: String,
    val city: City,
    val latitude: Double,
    val longitude: Double
): Serializable
