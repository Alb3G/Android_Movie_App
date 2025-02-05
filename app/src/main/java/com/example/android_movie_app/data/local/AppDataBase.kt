package com.example.android_movie_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android_movie_app.data.local.dao.CinemaDAO
import com.example.android_movie_app.data.local.dao.DataDAO
import com.example.android_movie_app.data.local.dao.MovieDAO
import com.example.android_movie_app.data.model.Cinema
import com.example.android_movie_app.data.model.Movie
import com.example.android_movie_app.data.model.MovieCinema
import com.example.android_movie_app.data.provider.Provider
import com.example.android_movie_app.utils.ioThread

@Database(
    entities = [Movie::class, Cinema::class, MovieCinema::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO
    abstract fun cinemaDAO(): CinemaDAO
    abstract fun dataDAO(): DataDAO

    companion object {
        @Volatile
        private var INSTANCE : AppDataBase? = null
        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            databaseBuilder(context.applicationContext,
                AppDataBase::class.java, "App.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context)
                                .dataDAO()
                                .preloadData(
                                    Provider.movieList,
                                    Provider.cinemaList,
                                    Provider.preloadMovieCinemaRelations()
                                )
                        }
                    }
                }).build()
    }
}