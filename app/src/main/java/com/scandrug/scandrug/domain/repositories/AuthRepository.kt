package com.scandrug.scandrug.domain.repositories
import com.bnkit.bnkit.data.resources.Resource
import com.google.firebase.firestore.DocumentSnapshot
import retrofit2.Response
import retrofit2.http.Body

interface AuthRepository {
    suspend fun loginUserFirebase(email: String, password: String): Resource<String>
    suspend fun registerUserFirebase(email: String, password: String): Resource<String>
    suspend fun resetPasswordUser(email: String): Resource<String>
}