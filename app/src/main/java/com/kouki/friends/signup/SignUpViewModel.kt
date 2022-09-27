package com.kouki.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.validation.CredentialsValidationResult
import com.kouki.friends.domain.validation.RegexCredentialValidator

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialValidator
) {
    private val _mutableSignUpstate = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpstate

    private val usersForPassword = mutableMapOf<String, MutableList<User>>()

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
            is CredentialsValidationResult.Valid -> {
                val isKnown = usersForPassword.values
                    .flatten()
                    .any { it.email == email }
                if(isKnown){
                    _mutableSignUpstate.value = SignUpState.DuplicateAccount
                } else{
                    val userId = email.takeWhile { it != '@' } + "Id"
                    val user = User(userId, email, about)
                    usersForPassword.getOrPut(password, ::mutableListOf).add(user)
                    _mutableSignUpstate.value = SignUpState.SignedUp(user)
                }
            }
        }
    }

}
