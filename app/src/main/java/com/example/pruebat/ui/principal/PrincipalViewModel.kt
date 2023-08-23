package com.example.pruebat.ui.principal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.core.Constantes
import com.example.pruebat.data.remote.api.RetrofitClient
import com.example.pruebat.models.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrincipalViewModel() : ViewModel() {

    private var currentPage = 1

    private var _navigateToSaved = MutableLiveData<Boolean>()
    val navigateToSaved: LiveData<Boolean>
        get() = _navigateToSaved

    private var _movieList = MutableLiveData<List<MovieModel>>()
    val movieList:LiveData<List<MovieModel>>
        get() = _movieList

    fun getMovieList(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.getMovies(Constantes.API_KEY,currentPage)
            withContext(Dispatchers.Main){
                _movieList.value = response.body()!!.results
            }
        }
    }

    fun getMoreMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            currentPage++
            val response = RetrofitClient.webService.getMovies(Constantes.API_KEY,currentPage)
            withContext(Dispatchers.Main){
                val newMovies = response.body()!!.results ?: emptyList()
                val currentMovies = movieList.value ?: emptyList()
                _movieList.postValue(currentMovies + newMovies)
            }
        }
    }

    fun navigateToSavedActivity(){
        _navigateToSaved.value = true
    }

    fun navigateToSavedActivityDone(){
        _navigateToSaved.value = false
    }
}