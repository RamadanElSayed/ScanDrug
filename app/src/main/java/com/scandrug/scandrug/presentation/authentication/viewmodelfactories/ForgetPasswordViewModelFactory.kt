package com.scandrug.scandrug.presentation.authentication.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.presentation.authentication.viewmodel.ForgetPasswordViewModel

class ForgetPasswordViewModelFactory(private val authUseCases: AuthUseCases) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForgetPasswordViewModel(authUseCases) as T
    }
}