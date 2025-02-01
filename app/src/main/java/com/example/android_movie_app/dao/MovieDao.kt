package com.example.android_movie_app.dao

import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.android_movie_app.DBOpenHelper
import com.example.android_movie_app.model.Movie
import com.example.android_movie_app.provider.MovieProvider

class MovieDao: DAO<Movie> {

    companion object {
        private const val UPDATE_QUERY = "UPDATE Movie SET title=?,description=?,imgResId=?,duration=?,releaseYear=?,country=?, uri=? WHERE id=?;"
    }

    override fun findAll(context: Context?): MutableList<Movie> {
        lateinit var res:MutableList<Movie>
        lateinit var c: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val query = "SELECT * FROM Movie;"
            c = db.rawQuery(query, null)
            res = mutableListOf()
            while(c.moveToNext()) {
                val newMovie = Movie(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6),
                    c.getString(7),
                )
                res.add(newMovie)
            }
        } finally {
            c.close()
        }
        return res
    }

    override fun save(context: Context?, t: Movie) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val st = db.compileStatement("INSERT INTO Movie (title,description,imgResId,duration,releaseYear,country,uri) " +
                "values (?,?,?,?,?,?,?);")
        try {
            st.bindString(1, t.title)
            st.bindString(2, t.description)
            st.bindString(3, t.imgResId.toString())
            st.bindString(4, t.duration.toString())
            st.bindString(5, t.releaseYear.toString())
            st.bindString(6, t.country)
            st.bindString(7, t.uri)
            st.executeInsert()
        } finally {
            st.close()
            db.close()
        }
    }

    override fun update(
        context: Context?,
        t: Movie
    ) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val st = db.compileStatement(UPDATE_QUERY)
        try {
            st.bindString(1, t.title)
            st.bindString(2, t.description)
            st.bindString(3, t.imgResId.toString())
            st.bindString(4, t.duration.toString())
            st.bindString(5, t.releaseYear.toString())
            st.bindString(6, t.country)
            st.bindString(7, t.uri)
            st.bindString(8, t.id.toString())
            st.executeUpdateDelete()
        } finally {
            st.close()
            db.close()
        }
    }

    override fun delete(
        context: Context?,
        t: Movie
    ) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val st = db.compileStatement("DELETE FROM Movie where id = ?;")
        try {
            st.bindString(1, t.id.toString())
            st.executeUpdateDelete()
        } finally {
            st.close()
            db.close()
        }
    }

    fun deleteAll(context: Context?) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL("DELETE FROM Movie;")
        db.close()
    }

    fun getOrgList(context: Context?): MutableList<Movie> {
        deleteAll(context)
        for(movie:Movie in MovieProvider.movieList) {
            save(context, movie)
        }
        return findAll(context)
    }

    fun getMovieById(context: Context?, id: Int): Movie {
        lateinit var res: Movie
        lateinit var c: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery("SELECT * FROM Movie WHERE id=?", arrayOf(id.toString()))
            while (c.moveToNext()) {
                res = Movie(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6),
                    c.getString(7),
                )
            }
        } finally {
            c.close()
            Log.d("Movie: ", res.title)
        }
        return res
    }
}