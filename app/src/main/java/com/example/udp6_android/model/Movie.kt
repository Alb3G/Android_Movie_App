package com.example.udp6_android.model

import java.io.Serializable

data class Movie(
    val id: Int,
    var title: String,
    val description: String,
    val imgResId: Int,
    val duration: Int,
    val releaseYear: Int,
    val country: String
): Serializable
