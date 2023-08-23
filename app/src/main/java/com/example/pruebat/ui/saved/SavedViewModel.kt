package com.example.pruebat.ui.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pruebat.data.local.MovieDataBase
import com.example.pruebat.domain.repository.MovieSavedRepository
import com.example.pruebat.models.MovieSaved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedViewModel(application: Application): AndroidViewModel(application) {

    private val repository:MovieSavedRepository
    val readSavedMovies: LiveData<List<MovieSaved>>


    init {
        val dao = MovieDataBase.getDatabase(application).dao()
        repository = MovieSavedRepository(dao)
        readSavedMovies = repository.readSavedMovies

    }

    fun searchMovie(title:String): LiveData<List<MovieSaved>>{
        return repository.searchMovieByTitle(title).asLiveData()
    }
}