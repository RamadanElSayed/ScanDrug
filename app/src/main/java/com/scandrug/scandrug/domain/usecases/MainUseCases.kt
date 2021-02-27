package com.scandrug.scandrug.domain.usecases
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.scandrug.scandrug.domain.repositories.MainRepository
import com.scandrug.scandrug.utils.Validator

class MainUseCases(private val mainRepository: MainRepository) {
//    suspend fun getCurrentUserData(): DocumentSnapshot? {
//        return mainRepository.getCurrentUserData()
//    }

    fun getCurrentLanguage(): String {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language
    }
    fun validateEmptyFiled(message: String): Boolean {
        return Validator.validateEmptyFiled(message)
    }
}