package com.scandrug.scandrug.presentation.authentication.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.presentation.authentication.viewmodel.LoginViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel

class LoginViewModelFactory( private val authUseCases: AuthUseCases) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(authUseCases) as T
    }
}

