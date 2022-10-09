package com.kouki.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouki.friends.app.CoroutineDispatchers
import com.kouki.friends.domain.user.UserRepository
import com.kouki.friends.domain.validation.CredentialsValidationResult
import com.kouki.friends.domain.validation.RegexCredentialValidator
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialValidator,
    private val userRepository: UserRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {
    private val mutableSignUpstate = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = mutableSignUpstate


    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (credentialsValidator.validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                mutableSignUpstate.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                mutableSignUpstate.value = SignUpState.BadPassword
            is CredentialsValidationResult.Valid ->
                proceedWithSignUp(email, password, about)
        }
    }

    private fun proceedWithSignUp(email: String, password: String, about: String) {
        viewModelScope.launch {
            mutableSignUpstate.value = SignUpState.Loading
            mutableSignUpstate.value = withContext(dispatchers.background) {
                userRepository.signUp(email, password, about)
            }
        }

    }
}
