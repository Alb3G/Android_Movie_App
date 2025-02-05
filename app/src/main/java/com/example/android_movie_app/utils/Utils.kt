package com.example.android_movie_app.utils

import com.example.android_movie_app.data.model.Cinema

fun List<Cinema>.getRandomCinemaId(): Int = this.random().id