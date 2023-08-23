package com.example.pruebat.ui.saved

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pruebat.R
import com.example.pruebat.databinding.RecyclerItemMovieBinding
import com.example.pruebat.models.MovieSaved

class SavedAdapter(val context: Context):RecyclerView.Adapter<SavedAdapter.ViewHolder>() {

    private var movieSaved = emptyList<MovieSaved>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemMovieBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedAdapter.ViewHolder, position: Int) {
        val movie = movieSaved[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieSaved.size
    }

    lateinit var onItemClickListener:(MovieSaved) -> Unit


    inner class ViewHolder(private val binding: RecyclerItemMovieBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieSaved){
            Glide.with(context)
                .load(movie.poster)
                .apply(RequestOptions().override(330,330))
                .into(binding.imageMovie)
            binding.root.setOnClickListener {
                onItemClickListener(movie)
            }
        }
    }

    fun setMovies(movies: List<MovieSaved>){
        this.movieSaved = movies
        notifyDataSetChanged()
    }

}