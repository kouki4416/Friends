package com.kouki.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

class SignUpViewModel {
    private val _mutableSignUpstate = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpstate

    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                _mutableSignUpstate.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                _mutableSignUpstate.value = SignUpState.BadPassword
        }
    }

    private fun validate(
        email: String,
        password: String
    ): CredentialsValidationResult {
        val emailRegex =
            """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""
        val emailPattern = Pattern.compile(emailRegex)
        val passwordRegex = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=\-]).{8,}$"""
        val passwordPattern = Pattern.compile(passwordRegex)

        val result = if (!emailPattern.matcher(email).matches()) {
            CredentialsValidationResult.InvalidEmail
        } else if (!passwordPattern.matcher(password).matches()) {
            CredentialsValidationResult.InvalidPassword
        } else TODO()
        return result
    }

    sealed class CredentialsValidationResult {
        object InvalidEmail : CredentialsValidationResult()
        object InvalidPassword : CredentialsValidationResult()
    }

}
