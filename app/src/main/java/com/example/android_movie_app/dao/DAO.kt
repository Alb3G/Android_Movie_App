package com.example.android_movie_app.dao

import android.content.Context

interface DAO<T> {
    fun findAll(context: Context?): List<T>
    fun save(context: Context?, t: T)
    fun update(context: Context?, t: T)
    fun delete(context: Context?, t: T)
}