package com.example.pruebat.ui.saved

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pruebat.databinding.ActivitySavedBinding
import com.example.pruebat.models.MovieSaved
import com.example.pruebat.ui.details.DetailActivity

class SavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBinding
    private lateinit var savedViewModel: SavedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //initialize savedViewModel
        savedViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)

        //initialize observers
        initObservers()

        //initialize listeners
        initListeners()
    }

    private fun initListeners() {
        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                if (searchQuery != null){
                    searchMovie(searchQuery)
                }
                return true
            }

            override fun onQueryTextChange(searchQuery: String?): Boolean {
                if (searchQuery != null){
                    searchMovie(searchQuery)
                }
                return true
            }
        })
    }

    private fun searchMovie(searchQuery: String) {
        val search = "%$searchQuery%"
        savedViewModel.searchMovie(search).observe(this){
            list ->
            list.let {
                configurateRecycler(it)
            }
        }
    }


    private fun initObservers() {
        savedViewModel.readSavedMovies.observe(this){
            configurateRecycler(it)
        }
    }

    fun configurateRecycler(list: List<MovieSaved>){
        val adapter = SavedAdapter(this)
        binding.recyclerPeliculas.adapter = adapter
        binding.recyclerPeliculas.layoutManager = GridLayoutManager(this,3)
        adapter.setMovies(list)
        adapter.onItemClickListener = {
            putDataAndNaavigate(
                it.id,
                it.title,
                it.poster,
                it.rate,
                it.desc)
            binding.searchMovie.setQuery("",false)
            binding.searchMovie.isIconified = true
        }
    }
    //fun to put data and start detailActivity
    private fun putDataAndNaavigate(
        id: String,
        title: String,
        poster: String,
        vote: String,
        desc: String
    ) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra("id",id)
            .putExtra("title",title)
            .putExtra("poster",poster)
            .putExtra("description",desc)
            .putExtra("rating",vote)
        startActivity(intent)
    }


}