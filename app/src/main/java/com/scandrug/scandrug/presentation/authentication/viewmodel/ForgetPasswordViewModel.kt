package com.scandrug.scandrug.presentation.authentication.viewmodel
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.resources.Resource
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import kotlinx.coroutines.Dispatchers

class ForgetPasswordViewModel(private val authUseCases: AuthUseCases) : ViewModel() {

    companion object {
        const val TAG = "ContactUsViewModel"
    }

    val isError: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private var appPreferences: AppPreferences = AppPreferences(sharedPreferences)
    private var token: String = appPreferences.getAccessToken().toString()


    fun validateEmail(email: String): Boolean {
        return authUseCases.validateEmail(email)
    }

    fun validatePassword(password: String): Boolean {
        return authUseCases.validatePassword(password)
    }
    fun resetPassword(email: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val response = authUseCases.resetPasswordUser(email)
                emit(response)
            }catch (e: Exception) {
                emit(Resource.Failure(e.message.toString()))
            }
        }

}