package com.scandrug.scandrug.data.repository

import com.scandrug.scandrug.data.resources.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.scandrug.scandrug.domain.repositories.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepoImp() : AuthRepository {

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override suspend fun loginUserFirebase(email: String, password: String): Resource<String> {
        auth.signInWithEmailAndPassword(email, password).await()
        return Resource.Success("")
    }

    override suspend fun registerUserFirebase(email: String, password: String): Resource<String> {
        auth.createUserWithEmailAndPassword(email, password).await()
        return Resource.Success("")
    }

    override suspend fun resetPasswordUser(email: String): Resource<String> {
        auth.sendPasswordResetEmail(email).await()
        return Resource.Success("")
    }

}