package com.example.pruebat.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pruebat.data.local.MovieDao
import com.example.pruebat.models.MovieSaved
import kotlinx.coroutines.flow.Flow

//repository for movies from Database requests
class MovieSavedRepository(private val dao: MovieDao) {
    suspend fun addMovie(movie:MovieSaved){
        dao.addMovie(movie)
    }
    val readSavedMovies:LiveData<List<MovieSaved>> = dao.getSavedMovie()

    suspend fun deleteMovie(movie:MovieSaved){
        dao.deleteMovie(movie)
    }


    fun searchMovieByTitle(title:String): Flow<List<MovieSaved>>{
        return dao.getSavedMovieByTitle(title)
    }

    fun existMovieInSaved(id:String): Flow<List<MovieSaved>>? {
        return dao.existsMovie(id)
    }
}