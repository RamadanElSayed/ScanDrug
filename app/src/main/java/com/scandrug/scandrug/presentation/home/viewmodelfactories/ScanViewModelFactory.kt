package com.scandrug.scandrug.presentation.home.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel

class ScanViewModelFactory(private val mainUseCases: MainUseCases) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScanViewModel(mainUseCases) as T
    }
}

