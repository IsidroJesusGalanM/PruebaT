package com.example.pruebat.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.domain.usecase.FirebaseLoginUseCase
import com.example.pruebat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: FirebaseLoginUseCase,
) : ViewModel() {

    //declare variables
    private val _loginState:MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val loginState: LiveData<Resource<Boolean>>
        get() = _loginState

    private val _navigateRegister = MutableLiveData<Boolean>()
    val navigateRegister:LiveData<Boolean>
        get() = _navigateRegister

    //login fun and change the status of loginState
    fun login(email:String, password:String){
        viewModelScope.launch {
            loginUseCase(email, password).onEach { state ->
                _loginState.value = state

            }.launchIn(viewModelScope)
        }
    }

    // start the process of nav to Register
    fun goToRegister(){
        _navigateRegister.value = true
    }

    // End the process of nav to Register
    fun navToRegisterDone(){
        _navigateRegister.value = false
    }

    // check if logged with shared preference
    fun setSharedPreferences(logged: Boolean, context: Context) {
        viewModelScope.launch(Dispatchers.IO){
            val sharedPreferences = context.getSharedPreferences("loggedControl",Context.MODE_PRIVATE)
            sharedPreferences.edit()
                .putBoolean("logged", logged)
                .apply()
        }
    }

    // get the logged saved status
    fun getSharedPref(context: Context):Boolean{
        val sharedPreferences = context.getSharedPreferences("loggedControl",Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("logged",false)
    }

}