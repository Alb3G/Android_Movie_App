package com.example.udp6_android.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.util.Log
import com.example.udp6_android.DBOpenHelper
import com.example.udp6_android.model.Cinema
import com.example.udp6_android.model.City

class CinemaDAO: DAO<Cinema> {

    companion object {
        private const val SELECT_ALL_CINEMAS = "SELECT * FROM Cinema;"
        private const val SELECT_CINEMA_BY_ID = "SELECT * FROM Cinema where id = ?;"
        private const val INSERT_CINEMA = "INSERT INTO Cinema (name,city,latitude,longitude) values (?,?,?,?);"
        private const val UPDATE_CINEMA = "UPDATE Cinema SET name = ?, city = ?, latitude = ?, longitude = ? where id = ?;"
        private const val DELETE_CINEMA = "DELETE FROM Cinema where id = ?;"
        private const val SELECT_MOVIE_CINEMA_RELATIONS = "SELECT * FROM Movie_Cinema where movie_id = ?;"
    }

    override fun findAll(context: Context?): List<Cinema> {
        val cinemas: MutableList<Cinema> = mutableListOf()
        lateinit var c: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_ALL_CINEMAS, null)
            while(c.moveToNext()) {
                cinemas.add(
                    Cinema(
                        c.getInt(0),
                        c.getString(1),
                        City.valueOf(c.getString(2)),
                        c.getDouble(3),
                        c.getDouble(4)
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("FindAll FAIL", e.message.toString())
        } finally {
            c.close()
        }

        return cinemas
    }

    override fun delete(context: Context?, t: Cinema) {
        lateinit var db: SQLiteDatabase
        lateinit var st: SQLiteStatement

        try {
            db = DBOpenHelper.getInstance(context)!!.writableDatabase
            st = db.compileStatement(INSERT_CINEMA)
            st.bindString(1, t.name)
            st.bindString(2, t.city.name)
            st.bindDouble(3, t.latitude)
            st.bindDouble(4, t.longitude)
            st.executeInsert()
        } catch (e: Exception) {
            Log.e("Delete Cinema FAIl", e.message.toString())
        } finally {
            st.close()
            db.close()
        }
    }

    override fun update(context: Context?, t: Cinema) {
        lateinit var db: SQLiteDatabase
        lateinit var st: SQLiteStatement

        try {
            db = DBOpenHelper.getInstance(context)!!.writableDatabase
            st = db.compileStatement(UPDATE_CINEMA)
            st.bindString(1, t.name)
            st.bindString(2, t.city.name)
            st.bindDouble(3, t.latitude)
            st.bindDouble(4, t.longitude)
            st.bindLong(5, t.id.toLong())
            st.executeUpdateDelete()
        } catch (e: Exception) {
            Log.e("UPDATE Cinema FAIL", e.message.toString())
        } finally {
            st.close()
            db.close()
        }
    }

    override fun save(context: Context?, t: Cinema) {
        lateinit var db: SQLiteDatabase
        lateinit var st: SQLiteStatement

        try {
            db = DBOpenHelper.getInstance(context)!!.writableDatabase
            st = db.compileStatement(DELETE_CINEMA)
            st.bindLong(1, t.id.toLong())
            st.executeUpdateDelete()
        } catch (e: Exception) {
            Log.e("SAVE Cinema FAIl", e.message.toString())
        } finally {
            st.close()
            db.close()
        }
    }

    fun getMovieCinemaRelations(context: Context?, id: Int): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        lateinit var db: SQLiteDatabase
        lateinit var c: Cursor
        try {
            db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_MOVIE_CINEMA_RELATIONS, arrayOf(id.toString()))
            while(c.moveToNext()) {
                list.add(c.getInt(1))
                Log.d("Cinema ID", "cinema id: ${c.getInt(1)}")
            }
        } catch (e: Exception) {
            Log.e("RELATIONS Fail", e.message.toString())
        } finally {
            c.close()
            db.close()
        }

        return list
    }

    fun findById(context: Context?, id: Int): Cinema {
        lateinit var cinema: Cinema
        lateinit var db: SQLiteDatabase
        lateinit var c: Cursor
        try {
            db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_CINEMA_BY_ID, arrayOf(id.toString()))
            Log.d("SQL Query", "Query: $SELECT_CINEMA_BY_ID, ID: $id")
            if(c.moveToNext()) {
                cinema = Cinema(
                    c.getInt(0),
                    c.getString(1),
                    City.valueOf(c.getString(2)),
                    c.getDouble(3),
                    c.getDouble(4)
                )
            }
        } catch (e: Exception) {
            Log.e("FindAll FAIL", e.message.toString())
        } finally {
            c.close()
            db.close()
        }

        return cinema
    }
}