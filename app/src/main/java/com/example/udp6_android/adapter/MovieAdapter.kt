package com.example.udp6_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.udp6_android.R
import com.example.udp6_android.model.Movie
import com.example.udp6_android.utils.MovieUtil
import com.example.udp6_android.viewHolder.MovieViewHolder

class MovieAdapter(
    private var movieList: List<Movie>,
    private val onClick: (Movie) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {
    fun updateList(newList: List<Movie>) {
        val movieUtil = MovieUtil(movieList, newList)
        val result = DiffUtil.calculateDiff(movieUtil)
        movieList = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
        holder.render(item, onClick)
    }

    fun setFilteredList(list: MutableList<Movie>) {
        notifyItemRangeChanged(0, list.size)
        movieList = list
        notifyItemRangeChanged(0, list.size)
    }

    fun getMovies():List<Movie> = movieList
}