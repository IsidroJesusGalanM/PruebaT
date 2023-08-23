package com.example.pruebat.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pruebat.data.local.MovieDataBase
import com.example.pruebat.domain.repository.MovieSavedRepository
import com.example.pruebat.models.MovieSaved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val repository:MovieSavedRepository
    init {
        val dao = MovieDataBase.getDatabase(application).dao()
        repository = MovieSavedRepository(dao)
    }
    fun addData(movie:MovieSaved){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovie(movie)
        }
    }

    fun deleteMovieSaved(movie:MovieSaved){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    fun existsMovie(id:String) :LiveData<Boolean>{
        val mediator = MediatorLiveData<Boolean>()
        repository.existMovieInSaved(id)?.onEach { movieList ->
            if(movieList.isEmpty()){
                mediator.postValue(false)
            }else{
                mediator.postValue(true)
            }
        }?.launchIn(viewModelScope)
        return mediator
    }
}