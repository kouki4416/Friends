package com.kouki.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouki.friends.domain.validation.CredentialsValidationResult
import com.kouki.friends.domain.validation.RegexCredentialValidator

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialValidator
    ) {
    private val _mutableSignUpstate = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpstate

    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (credentialsValidator.validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                _mutableSignUpstate.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                _mutableSignUpstate.value = SignUpState.BadPassword
            else -> null
        }
    }

}
