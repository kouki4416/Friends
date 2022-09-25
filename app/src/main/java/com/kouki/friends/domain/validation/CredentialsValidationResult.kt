package com.kouki.friends.domain.validation

sealed class CredentialsValidationResult {
    object InvalidEmail : CredentialsValidationResult()
    object InvalidPassword : CredentialsValidationResult()
}