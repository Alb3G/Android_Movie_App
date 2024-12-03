package com.example.udp6_android.dao

import android.content.Context
import android.database.Cursor
import com.example.udp6_android.DBOpenHelper
import com.example.udp6_android.model.Movie
import com.example.udp6_android.provider.MovieProvider

class MovieDao: DAO<Movie>  {
    override fun findAll(context: Context?): MutableList<Movie> {
        lateinit var res:MutableList<Movie>
        lateinit var c: Cursor
        try {
            // Obtener acceso de solo lectura
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val query = "SELECT * FROM Movie;"
            c = db.rawQuery(query, null)
            res = mutableListOf()
            while(c.moveToNext()) {
                val nueva = Movie(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6)
                )
                res.add(nueva)
            }
        } finally {
            c.close()
        }
        return res
    }

    override fun save(context: Context?, t: Movie) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val st = db.compileStatement("INSERT INTO Movie (title,description,imgResId,duration,releaseYear,country) " +
                "values (?,?,?,?,?,?);")
        st.bindString(1, t.title)
        st.bindString(2, t.description)
        st.bindString(3, t.imgResId.toString())
        st.bindString(4, t.duration.toString())
        st.bindString(5, t.releaseYear.toString())
        st.bindString(6, t.country)
        st.executeInsert()
        st.close()
        db.close()
    }

    override fun update(
        context: Context?,
        t: Movie
    ) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val st = db.compileStatement("UPDATE Movie SET title=? WHERE id=${t.id};")
        st.bindString(1, t.title)
        st.executeUpdateDelete()
        st.close()
        db.close()
    }

    override fun delete(
        context: Context?,
        t: Movie
    ) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val st = db.compileStatement("DELETE FROM Movie where id = ?;")
        st.bindString(1, t.id.toString())
        st.executeUpdateDelete()
        st.close()
        db.close()
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
}