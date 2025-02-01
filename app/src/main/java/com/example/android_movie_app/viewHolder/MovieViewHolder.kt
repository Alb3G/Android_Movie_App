package com.example.android_movie_app.viewHolder

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.android_movie_app.databinding.MovieItemBinding
import com.example.android_movie_app.model.Movie

class MovieViewHolder(view: View): ViewHolder(view), View.OnCreateContextMenuListener {
    val binding = MovieItemBinding.bind(view)
    lateinit var movie: Movie

    fun render(item: Movie, onClick: (Movie) -> Unit) {
        movie = item
        binding.movieImg.setImageResource(item.imgResId)
        Glide.with(binding.movieImg.context).load(item.imgResId).centerCrop().into(binding.movieImg)
        binding.movieTitle.text = item.title
        itemView.setOnClickListener {
            onClick(item)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(movie.title)
        menu.add(this.adapterPosition, 0,0,"Delete")
        menu.add(this.adapterPosition, 1, 1, "Edit")
        menu.add(this.adapterPosition, 2, 2, "Detail")
        menu.add(this.adapterPosition, 3, 3, "Take Photo")
        menu.add(this.adapterPosition, 4, 4, "See Photo")
    }
}