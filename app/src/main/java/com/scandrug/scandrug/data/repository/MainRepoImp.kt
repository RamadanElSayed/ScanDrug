package com.scandrug.scandrug.data.repository
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.domain.repositories.MainRepository

class MainRepoImp(): MainRepository {

    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
//    val auth = FirebaseAuth.getInstance()
//    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

//    override suspend fun getCurrentUserData(): DocumentSnapshot? {
//        return try {
//            val data = firestore.collection("users").document(user!!.uid)
//                .get()
//                .await()
//            data
//        } catch (e: Exception) {
//            null
//        }    }

}