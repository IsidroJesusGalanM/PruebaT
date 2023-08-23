package com.example.pruebat.data.remote.api.response

import com.example.pruebat.models.MovieModel
import com.google.gson.annotations.SerializedName

//class to Parse the results from the api response
data class MovieResponse(
    @SerializedName("results")
    var results: List<MovieModel>
)
