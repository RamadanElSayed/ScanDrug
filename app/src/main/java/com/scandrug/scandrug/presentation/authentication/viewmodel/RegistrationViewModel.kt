package com.scandrug.scandrug.presentation.authentication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.remotemodel.RegistrationModel
import com.scandrug.scandrug.data.resources.Resource
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class RegistrationViewModel(private val authUseCases: AuthUseCases) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val isError: SingleLiveEvent<String> = SingleLiveEvent()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val navigateToMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private var appPreferences: AppPreferences = AppPreferences(sharedPreferences)
    private var token: String = appPreferences.getAccessToken().toString()
    private lateinit var registrationModel: RegistrationModel
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()

    fun validateEmail(email: String): Boolean {
        return authUseCases.validateEmail(email)
    }

    fun validatePassword(password: String): Boolean {
        return authUseCases.validatePassword(password)
    }

    fun validateConfirmPassword(password: String, confirm: String): Boolean {
        return authUseCases.validateConfirmPassword(password, confirm)
    }


    fun validateMobileNumber(mobileNumber: String): Boolean {
        return authUseCases.validateMobileNumber(mobileNumber)
    }

    fun validateName(name: String): Boolean {
        return authUseCases.validateName(name)
    }

    fun registerUser(email: String, password: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val response = authUseCases.registerUser(email, password)
                emit(response)
            } catch (e: Exception) {
                emit(Resource.Failure(e.message.toString()))
            }
        }


    fun createUserWithNewAccount(
        preRegistrationModel: RegistrationModel
    ) {
        registrationModel = RegistrationModel()
        auth.currentUser!!.uid
        val items = HashMap<String, Any>()
        registrationModel.userName = preRegistrationModel.userName
        registrationModel.emailTxt = preRegistrationModel.emailTxt
        registrationModel.phoneNumber = preRegistrationModel.phoneNumber

        items["firstName"] = preRegistrationModel.userName
        items["email"] = preRegistrationModel.emailTxt
        items["phoneNumber"] = preRegistrationModel.phoneNumber
        saveUserToDatabase(auth.currentUser!!, items)
    }

    private fun saveUserToDatabase(user: FirebaseUser, items: HashMap<String, Any>) {

        database.collection("users").document(user.uid).set(items)
            .addOnSuccessListener {
                loading.value = false
                appPreferences.setAccessToken("company")
                navigateToMain.value = true
            }.addOnFailureListener {
                loading.value = false

            }
    }
}