package com.scandrug.scandrug.presentation.home.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.viewmodel.CartViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.CompletedViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel

class CompletedViewModelFactory(private val mainUseCases: MainUseCases) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompletedViewModel(mainUseCases) as T
    }
}

