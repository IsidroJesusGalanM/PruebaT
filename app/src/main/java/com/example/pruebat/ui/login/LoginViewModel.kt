package com.example.pruebat.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.domain.usecase.FirebaseLoginUseCase
import com.example.pruebat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: FirebaseLoginUseCase
) : ViewModel() {

    private val _loginState:MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val loginState: LiveData<Resource<Boolean>>
        get() = _loginState

    private val _navigateRegister = MutableLiveData<Boolean>()
    val navigateRegister:LiveData<Boolean>
        get() = _navigateRegister


    fun login(email:String, password:String){
        viewModelScope.launch {
            loginUseCase(email, password).onEach { state ->
                _loginState.value = state
            }.launchIn(viewModelScope)
        }
    }

    fun goToRegister(){
        _navigateRegister.value = true
    }

    fun navToRegisterDone(){
        _navigateRegister.value = false
    }


}