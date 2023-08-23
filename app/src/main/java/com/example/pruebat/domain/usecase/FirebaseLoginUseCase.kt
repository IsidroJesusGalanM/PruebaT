package com.example.pruebat.domain.usecase

import com.example.pruebat.domain.repository.AuthRepository
import com.example.pruebat.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Login Use case
class FirebaseLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        if (authRepository.login(email,password)){
            emit(Resource.Success(true))
            emit(Resource.Finished)
        }else{
            emit(Resource.Error("login error"))
            emit(Resource.Finished)
        }
    }
}