package com.example.android_movie_app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Cinemas")
@Parcelize
data class Cinema(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val city: City,
    val latitude: Double,
    val longitude: Double
): Parcelable
