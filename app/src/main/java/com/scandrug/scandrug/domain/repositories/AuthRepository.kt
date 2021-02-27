package com.scandrug.scandrug.domain.repositories
import com.scandrug.scandrug.data.resources.Resource

interface AuthRepository {
    suspend fun loginUserFirebase(email: String, password: String): Resource<String>
    suspend fun registerUserFirebase(email: String, password: String): Resource<String>
    suspend fun resetPasswordUser(email: String): Resource<String>
}