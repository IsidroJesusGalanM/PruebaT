package com.example.pruebat.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebat.models.MovieModel

//DAO for the movies from Api
@Dao
interface MovieApiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieApi(movie:MovieModel)

    @Query("SELECT * FROM moviesApi")
    fun getMovieApi(): LiveData<List<MovieModel>>

    @Query("DELETE FROM moviesApi")
    fun deleteAllMovies()
}