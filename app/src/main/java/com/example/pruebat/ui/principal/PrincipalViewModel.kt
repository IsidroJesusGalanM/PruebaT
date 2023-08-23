package com.example.pruebat.ui.principal

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pruebat.core.Constantes
import com.example.pruebat.data.local.MovieDataBase
import com.example.pruebat.data.remote.api.RetrofitClient
import com.example.pruebat.domain.repository.MovieApiRepository
import com.example.pruebat.models.MovieModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrincipalViewModel(application: Application) : AndroidViewModel(application) {

    //var to define the page of the API TMBD
    private var currentPage = 1
    //repository var
    private val repositoryMovieApi:MovieApiRepository
    //var to read the movies on the Database where from the api
    val readMoviesFromApi: LiveData<List<MovieModel>>

    init {
        //initialize DAO for the repository
        val dao = MovieDataBase.getDatabase(application).daoMovieApi()
        repositoryMovieApi = MovieApiRepository(dao)
        readMoviesFromApi = repositoryMovieApi.readMoviesApi
    }

    //var to navigate to SavedActivity
    private var _navigateToSaved = MutableLiveData<Boolean>()
    val navigateToSaved: LiveData<Boolean>
        get() = _navigateToSaved

    //var to navigate to loginActivty
    private var _navigateTologin= MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateTologin

    // get Movies from api and saved on ROOM database
     fun getMovieList() {
            viewModelScope.launch(Dispatchers.IO) {
                repositoryMovieApi.removeMoviesApi()
                val response = RetrofitClient.webService.getMovies(Constantes.API_KEY, currentPage)
                withContext(Dispatchers.Main) {
                    val firstMovieList = response.body()!!.results
                    firstMovieList.forEach{
                        repositoryMovieApi.addMoviesApi(it)
                    }
                }
            }
    }


    //get more movies if scrollEnd from api and saved on ROOM database
    fun getMoreMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            currentPage++
            val response = RetrofitClient.webService.getMovies(Constantes.API_KEY,currentPage)
            withContext(Dispatchers.Main){
                val newMovies = response.body()!!.results ?: emptyList()
                newMovies.forEach{
                    repositoryMovieApi.addMoviesApi(it)
                }
            }
        }
    }

    //init the navigation to savedActivity
    fun navigateToSavedActivity(){
        _navigateToSaved.value = true
    }

    //end the navigation to savedActivity
    fun navigateToSavedActivityDone(){
        _navigateToSaved.value = false
    }

    //init the navigation to login Activity

    fun navigateToLoginActivity(){
        _navigateTologin.value = true
    }

    //end the navigation to loginActivity

    fun navigateToLoginActivityDone(){
        _navigateTologin.value = false
    }

    //logout and clear sharedPreference
    fun logout(context: Context) {
        FirebaseAuth.getInstance().signOut()
        val sharedPreferences = context.getSharedPreferences("loggedControl",Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }


    //check if Internet
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}