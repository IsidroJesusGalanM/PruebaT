package com.example.pruebat.di

import com.example.pruebat.data.remote.FirebaseAuthRepositoryImplementation
import com.example.pruebat.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepository: FirebaseAuthRepositoryImplementation): AuthRepository

}