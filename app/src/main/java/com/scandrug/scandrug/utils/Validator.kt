package com.scandrug.scandrug.utils

import java.util.regex.Pattern

object Validator {
    const val PASS_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*%^()_-])(?=\\S+$).{11,20}$"

    private const val ID_NUMBER_Length = 14
    private const val VERIFY_NUMBER_Length = 4
    private const val PASSWORD_NUMBER_Length = 6

    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private val ID_NUMBER = Pattern.compile(
        ""
    )

    private val FIRST_NAME = Pattern.compile(
        "^[a-zA-Z]+\$"
    )

    private val MOBILE_NUMBER = Pattern.compile(
        "(01)[0-9]{9}"
    )

    fun validateEmail(email: String): Boolean {
        return when {
            email.isBlank() ->
                false
            !EMAIL_ADDRESS.matcher(email).matches() ->
                false
            else ->
                true
        }

    }


    fun validatePassword(password: String): Boolean {
        val pattern = Pattern.compile(PASS_PATTERN)
        val matcher = pattern.matcher(password)
        return when {
            password.isBlank() ->
                false
            password.length< PASSWORD_NUMBER_Length ->
                false
            else ->
                true
        }
    }

    fun validateConfirmPassword(password: String, confirm: String): Boolean {
        return when {
            password.isBlank() ->
                false
            confirm != password ->
                false
            else ->
                true
        }
    }

    fun validateIdNumber(IdNumber: String): Boolean {
        return when {
            IdNumber.isBlank() ->
                false
            IdNumber.length!= ID_NUMBER_Length ->
                false
            else ->
                true
        }
    }

    fun validateVerificationCode(verifyCode: String): Boolean {
        return when {
            verifyCode.isBlank() ->
                false
            verifyCode.length!= VERIFY_NUMBER_Length ->
                false
            else ->
                true
        }
    }
    fun validateMobileNumber(mobileNumber: String): Boolean {
        return when {
            mobileNumber.isBlank() ->
                false
            !MOBILE_NUMBER.matcher(mobileNumber).matches() ->
                false
            else ->
                true
        }
    }
    fun validateEmptyFiled(message: String): Boolean {
        return when {
            message.isBlank() ->
                false
            else ->
                true
        }
    }

    fun validateName(name: String): Boolean {
        return when {
            name.isBlank() ->
                false
            !FIRST_NAME.matcher(name).matches() ->
                false
            else ->
                true
        }
    }
}