package com.example.pruebat.domain.repository


//repository for FirebaseAuth fun
interface AuthRepository {

    suspend fun login(email:String, password:String): Boolean
    suspend fun signup(email:String, password:String): Boolean

}