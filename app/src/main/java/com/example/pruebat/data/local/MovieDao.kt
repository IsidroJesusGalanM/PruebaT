package com.example.pruebat.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebat.models.MovieSaved
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie:MovieSaved)

    @Query("SELECT * FROM movieSaved ORDER BY id ASC")
    fun getSavedMovie(): LiveData<List<MovieSaved>>

    @Query("SELECT * FROM movieSaved WHERE title LIKE '%' || :title || '%' ")
    fun getSavedMovieByTitle(title:String): Flow<List<MovieSaved>>

    @Query("SELECT * FROM movieSaved WHERE id = :id")
    fun existsMovie(id:String): Flow<List<MovieSaved>>?

    @Delete
    suspend fun deleteMovie(movie:MovieSaved)

}