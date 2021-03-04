package com.scandrug.scandrug.presentation.home.delivery.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.delivery.viewmodel.DeliveryViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.CartViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.CompletedViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel

class DeliveryViewModelFactory(private val mainUseCases: MainUseCases) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeliveryViewModel(mainUseCases) as T
    }
}

