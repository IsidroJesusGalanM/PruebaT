package com.example.pruebat.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.domain.usecase.FirebaseLoginUseCase
import com.example.pruebat.domain.usecase.FirebaseSignUpUseCase
import com.example.pruebat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCas: FirebaseSignUpUseCase
) : ViewModel() {

    private val _singUpState:MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val singUpState: LiveData<Resource<Boolean>>
        get() = _singUpState

    private val _navLogin = MutableLiveData<Boolean>()
    val navlogin: LiveData<Boolean>
        get() = _navLogin

    fun register(email:String, password:String){
        viewModelScope.launch {
            signUpUseCas(email, password).onEach {
                _singUpState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun goToLogin(){
        _navLogin.value = true
    }

    fun navToLoginDone(){
        _navLogin.value = false
    }

    fun checkEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }
}