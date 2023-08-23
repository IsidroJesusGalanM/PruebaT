package com.example.pruebat.domain.repository

import androidx.lifecycle.LiveData
import com.example.pruebat.data.local.MovieApiDao
import com.example.pruebat.models.MovieModel

//Repository for movies from API request
class MovieApiRepository(private val dao:MovieApiDao) {
    suspend fun addMoviesApi(movieApi:MovieModel){
        dao.addMovieApi(movieApi)
    }

    fun removeMoviesApi(){
        dao.deleteAllMovies()
    }

    val readMoviesApi:LiveData<List<MovieModel>> = dao.getMovieApi()
}