package com.example.udp6_android.model

import java.io.Serializable

data class Cinema(
    val id: Int,
    val name: String,
    val city: City,
    val latitude: Double,
    val longitude: Double
): Serializable
