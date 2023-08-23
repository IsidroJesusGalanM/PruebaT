package com.example.pruebat.data.remote.api

import com.example.pruebat.core.Constantes
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Web request for the api TMDB
object RetrofitClient {
    val webService: WebService  by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }
}