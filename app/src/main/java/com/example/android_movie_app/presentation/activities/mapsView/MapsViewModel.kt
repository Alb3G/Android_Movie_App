package com.example.android_movie_app.presentation.activities.mapsView

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_movie_app.data.local.AppDataBase
import com.example.android_movie_app.data.local.repository.CinemaReporsitory
import com.example.android_movie_app.data.model.Cinema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsViewModel(
    application: Application
): AndroidViewModel(application) {

    private val cinemaReporsitory = CinemaReporsitory(AppDataBase.getInstance(application).cinemaDAO())
    private val _cinemas = MutableLiveData<List<Cinema>>()
    val cinemas: LiveData<List<Cinema>> get() = _cinemas

    fun getCinemasByMovieId(id: Int)  {
        viewModelScope.launch(Dispatchers.IO) {
            val cinemas = cinemaReporsitory.getCinemasByMovieId(id)
            withContext(Dispatchers.Main) { Log.d("Cinemas Res:", cinemas.toString()) }
            _cinemas.postValue(cinemaReporsitory.getCinemasByMovieId(id))
        }
    }

}