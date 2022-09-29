package com.kouki.friends.domain.validation

import java.util.regex.Pattern

class RegexCredentialValidator {

    private companion object {
        private const val EMAIL_REGEX =
            """^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}${'$'}"""
        private const val PASSWORD_REGEX =
            """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=\-]).{8,}$"""
    }

    private val emailPattern = Pattern.compile(EMAIL_REGEX)
    private val passwordPattern = Pattern.compile(PASSWORD_REGEX)

    fun validate(
        email: String,
        password: String
    ): CredentialsValidationResult {
        val emailPattern = emailPattern
        val passwordPattern = passwordPattern

        val result = if (!emailPattern.matcher(email).matches()) {
            CredentialsValidationResult.InvalidEmail
        } else if (!passwordPattern.matcher(password).matches()) {
            CredentialsValidationResult.InvalidPassword
        } else {
            CredentialsValidationResult.Valid
        }
        return result
    }
}