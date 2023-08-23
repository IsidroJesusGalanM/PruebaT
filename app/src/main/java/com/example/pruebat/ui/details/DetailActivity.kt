package com.example.pruebat.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pruebat.core.Constantes
import com.example.pruebat.databinding.ActivityDetailBinding
import com.example.pruebat.models.MovieSaved

class DetailActivity : AppCompatActivity() {

    //lateinit vars
    private lateinit var binding:ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    //Initialization of global lateinit variables
    lateinit var idMovie: String
    lateinit var nameMovie: String
    lateinit var descMovie: String
    lateinit var rateMovie: String
    lateinit var imageURL:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //ViewModel provide val
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        setup()
    }

    private fun setup() {
        setDataOnView()
        initListener()
        initObservers()
    }

    //initialize Listeners
    private fun initListener() {
        //save movie button
        binding.saveMovie.setOnClickListener {
            val moviePrueba = MovieSaved(idMovie,nameMovie,imageURL
            ,descMovie,rateMovie)

            detailViewModel.addData(moviePrueba)
            Toast.makeText(this, "Pelicula Guardada", Toast.LENGTH_SHORT).show()
        }

        //Delete movie button
        binding.deleteMovie.setOnClickListener {
            val movieData = MovieSaved(idMovie,nameMovie,imageURL
                ,descMovie,rateMovie)
            detailViewModel.deleteMovieSaved(movieData)
            Toast.makeText(this, "Pelicula Borrada", Toast.LENGTH_SHORT).show()
        }
    }


    //initialize observers
    private fun initObservers() {
        //observer for exist result
        detailViewModel.existsMovie(idMovie).observe(this){exist ->
            if (exist){
                binding.saveMovie.visibility = View.GONE
                binding.deleteMovie.visibility = View.VISIBLE
            }else{
                binding.deleteMovie.visibility = View.GONE
                binding.saveMovie.visibility = View.VISIBLE
            }
        }
    }

    //get and set the data of the bundle
    private fun setDataOnView() {
        //get data of the bundle
        val bundle = intent.extras!!
        idMovie = bundle.getString("id").toString()
        nameMovie = bundle.getString("title").toString()
        val poster = bundle.getString("poster")
        descMovie = bundle.getString("description").toString()
        rateMovie = bundle.getString("rating").toString()

        //set data of the bundle
        val imagePoster = binding.imageMovie
        binding.titleMovie.text = nameMovie
        binding.descMovie.text = descMovie

        //formate the data of rate
        val rateDoble = rateMovie.toDouble()
        val formatRate = String.format("%.1f", rateDoble)
        binding.ratingMovie.text = formatRate
        //concatenate string to obtain url Image
        imageURL = "${Constantes.BASE_URL_IMAGE}${poster}"

        Glide.with(this)
            .load(imageURL)
            .apply(RequestOptions().override(350,500).transform(RoundedCorners(12)))
            .into(imagePoster)



    }

}