package com.example.pruebat.data.remote

import android.util.Log
import com.example.pruebat.domain.repository.AuthRepository
import com.example.pruebat.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :AuthRepository{

    override suspend fun login(email: String, password: String): Boolean {
        return try{
            var isSuccessful = true
            firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener { isSuccessful = true }
                .addOnFailureListener{ isSuccessful = false }
                .await()
            isSuccessful
        }catch (e:Exception){
            Log.e("mal",e.toString())
             false
        }
    }

    override suspend fun signup(email: String, password: String): Boolean {
        return try {
            var isSuccessful = true
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {isSuccessful = true}
                .addOnFailureListener { isSuccessful = false }
                .await()
            isSuccessful
        }catch (e:Exception){
            false
        }
    }

}