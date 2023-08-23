package com.example.pruebat.ui.principal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebat.databinding.ActivityPrincipalBinding
import com.example.pruebat.ui.details.DetailActivity
import com.example.pruebat.ui.saved.SavedActivity

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var adapterMovie: MovieAdapter


    private val principalViewmodel: PrincipalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        initObservers()
        initListeners()

        principalViewmodel.getMovieList()

        recyclerViewContent()
    }

    private fun initListeners() {
        // start Saved Movies Activity
        binding.saved.setOnClickListener {
            principalViewmodel.navigateToSavedActivity()
        }

        //configure RecyclerView Scroll
        var isLoading = false
        binding.recyclerPeliculas.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.recyclerPeliculas.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisible = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading){
                    if((visibleItemCount + firstVisible) >= totalItemCount && firstVisible >= 0){
                        isLoading = true
                        principalViewmodel.getMoreMovies()
                    }
                }
            }
            //Action when scroll is Updated
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    isLoading = false
                }
            }
        })

    }

    private fun initObservers() {
        principalViewmodel.movieList.observe(this){
            adapterMovie.listMovie = it
            adapterMovie.notifyDataSetChanged()
        }

        principalViewmodel.navigateToSaved.observe(this){
            if (it){
                startActivity(Intent(this,SavedActivity::class.java))
                principalViewmodel.navigateToSavedActivityDone()
            }
        }
    }

    private fun recyclerViewContent() {
        val layoutManager = GridLayoutManager(this,3)
        binding.recyclerPeliculas.layoutManager = layoutManager
        adapterMovie = MovieAdapter(this, arrayListOf())
        binding.recyclerPeliculas.adapter = adapterMovie
        adapterMovie.onItemClickListener = {
            //invoke fun to putData
            putDataAndNaavigate(
                it.id,
                it.title,
                it.poster,
                it.vote,
                it.desc)
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
        val intent = Intent(this,DetailActivity::class.java)
            .putExtra("id",id)
            .putExtra("title",title)
            .putExtra("poster",poster)
            .putExtra("description",desc)
            .putExtra("rating",vote)
        startActivity(intent)
    }

}