package com.scandrug.scandrug.presentation.authentication.viewmodel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.resources.Resource
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LoginViewModel(private val authUseCases: AuthUseCases) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val isError: SingleLiveEvent<String> = SingleLiveEvent()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val baseUrlResponse: SingleLiveEvent<String> = SingleLiveEvent()

    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private var appPreferences: AppPreferences = AppPreferences(sharedPreferences)
    private var token: String = appPreferences.getAccessToken().toString()

    fun loginUser(email: String, password: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading(""))
            try {
                val response = authUseCases.loginUser(email, password)
                emit(response)
            } catch (e: Exception) {
                emit(Resource.Failure(e.message.toString()))
            }
        }

    fun validateEmail(email: String): Boolean {
        return authUseCases.validateEmail(email)
    }

    fun validatePassword(password: String): Boolean {
        return authUseCases.validatePassword(password)
    }


    fun registerUser(email: String, password: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading(""))
            try {
                val response = authUseCases.registerUser(email, password)
                emit(response)
            } catch (e: Exception) {
                emit(Resource.Failure(e.message.toString()))
            }
        }
}