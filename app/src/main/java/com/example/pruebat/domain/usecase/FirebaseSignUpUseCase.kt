package com.example.pruebat.domain.usecase

import com.example.pruebat.domain.repository.AuthRepository
import com.example.pruebat.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// register new user case
class FirebaseSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading)
        val signUpState = authRepository.signup(email, password)
        if (signUpState){
            emit(Resource.Success(true))
        }else{
            emit(Resource.Error("Error signing up"))
        }
    }
}