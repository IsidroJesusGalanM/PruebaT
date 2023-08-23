package com.example.pruebat.ui.principal

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pruebat.R
import com.example.pruebat.core.Constantes
import com.example.pruebat.databinding.RecyclerItemMovieBinding
import com.example.pruebat.models.MovieModel

//Movie APi adapter
class MovieAdapter(
    val context: Context,
    var listMovie: List<MovieModel>
    ):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemMovieBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    //set onItemClickListener function
    lateinit var onItemClickListener:(MovieModel) -> Unit


    inner class ViewHolder(private val binding:RecyclerItemMovieBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel){
            Glide
                .with(context)
                .load("${Constantes.BASE_URL_IMAGE}${movie.poster}")
                .apply(RequestOptions().override(330,330).transform(RoundedCorners(26)))
                .into(binding.imageMovie)

            binding.root.setOnClickListener {
                onItemClickListener(movie)
            }
        }
    }
}