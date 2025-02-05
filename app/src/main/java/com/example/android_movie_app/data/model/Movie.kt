package com.example.android_movie_app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Movies")
@Parcelize
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    val description: String,
    val imgResId: Int,
    val duration: Int,
    val releaseYear: Int,
    val country: String,
    val uri: String
): Parcelable
