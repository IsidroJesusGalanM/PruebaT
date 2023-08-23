package com.example.pruebat.data.remote.api

import com.example.pruebat.data.remote.api.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("movie/day")

    suspend fun getMovies(
        @Query("api_key") apiKey: String,@Query("page") page: Int
    ): Response<MovieResponse>
}