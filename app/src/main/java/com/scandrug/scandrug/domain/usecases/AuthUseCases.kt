package com.scandrug.scandrug.domain.usecases

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.bnkit.bnkit.data.resources.Resource
import com.scandrug.scandrug.domain.repositories.AuthRepository
import com.scandrug.scandrug.utils.Validator

class AuthUseCases(private val authRepository: AuthRepository) {

    suspend fun loginUser(email: String, password: String): Resource<String> {
        return authRepository.loginUserFirebase(email, password)
    }

    suspend fun registerUser(email: String, password: String): Resource<String> {
        return authRepository.registerUserFirebase(email, password)
    }

    fun validateEmail(email: String): Boolean {
        return Validator.validateEmail(email)
    }

    fun getCurrentLanguage(): String {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language
    }


    fun validatePassword(password: String): Boolean {
        return Validator.validatePassword(password)
    }

    suspend fun resetPasswordUser(email: String): Resource<String> {
        return authRepository.resetPasswordUser(email)
    }

    fun validateConfirmPassword(password: String, confirm: String): Boolean {
        return Validator.validateConfirmPassword(password, confirm)
    }

    fun validateIdNumber(IdNumber: String): Boolean {
        return Validator.validateIdNumber(IdNumber)
    }

    fun validateEmptyFiled(message: String): Boolean {
        return Validator.validateEmptyFiled(message)
    }

    fun validateMobileNumber(mobileNumber: String): Boolean {
        return Validator.validateMobileNumber(mobileNumber)
    }

    fun validateVerificationCode(verifyNumber: String): Boolean {
        return Validator.validateVerificationCode(verifyNumber)
    }

    fun validateName(name: String): Boolean {
        return Validator.validateName(name)
    }

}