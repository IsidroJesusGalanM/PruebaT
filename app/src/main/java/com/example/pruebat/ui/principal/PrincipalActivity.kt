package com.example.pruebat.ui.principal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebat.databinding.ActivityPrincipalBinding
import com.example.pruebat.ui.details.DetailActivity
import com.example.pruebat.ui.login.Login
import com.example.pruebat.ui.saved.SavedActivity

class PrincipalActivity : AppCompatActivity() {
    //lateinit vars
    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var adapterMovie: MovieAdapter
    private lateinit var principalViewModel: PrincipalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // set the viewModel provider
        principalViewModel = ViewModelProvider(this).get(PrincipalViewModel::class.java)
        setup()
    }

    private fun setup() {

        //kwon if the dispositive have internet connectivity
        if (principalViewModel.isInternetAvailable(this)){
            principalViewModel.getMovieList()
        }

        //Initialize the observers
        initObservers()
        //Initialize the Listeners
        initListeners()
        //set The RecyclerView data
        recyclerViewContent()
    }

    //fun to initialize Listeners
    private fun initListeners() {
        // start Saved Movies Activity
        binding.saved.setOnClickListener {
            principalViewModel.navigateToSavedActivity()
        }

        //logout and start login activity
        binding.logout.setOnClickListener {
            principalViewModel.logout(applicationContext)
            principalViewModel.navigateToLoginActivity()
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

                val afterEnd = 2

                if(principalViewModel.isInternetAvailable(this@PrincipalActivity)) {
                        if (visibleItemCount + firstVisible >= totalItemCount - afterEnd) {
                            principalViewModel.getMoreMovies()
                    }

                }else{
                    if(!isLoading){
                        if (visibleItemCount + firstVisible >= totalItemCount - afterEnd) {
                            Toast.makeText(
                                this@PrincipalActivity,
                                "Conectate a internet para descargar mas peliculas",
                                Toast.LENGTH_SHORT
                            ).show()
                            isLoading = true
                        }
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

    //fun to initialize observers
    private fun initObservers() {

        //Observe the function on viewModel to getMovies from ROOM database if change
        principalViewModel.readMoviesFromApi.observe(this){
            if (it.isEmpty()){
                adapterMovie.listMovie = emptyList()
            }else {
                adapterMovie.listMovie = it
                adapterMovie.notifyDataSetChanged()
            }
        }


        //Observe the change of activity to SavedActivity
        principalViewModel.navigateToSaved.observe(this){
            if (it){
                startActivity(Intent(this,SavedActivity::class.java))
                principalViewModel.navigateToSavedActivityDone()
            }
        }
        principalViewModel.navigateToLogin.observe(this){
            if (it){
                startActivity(Intent(this,Login::class.java))
                finish()
                principalViewModel.navigateToLoginActivityDone()
            }
        }
    }

    //set the information for the RecyclerView
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