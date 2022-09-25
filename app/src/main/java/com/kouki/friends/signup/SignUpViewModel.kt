package com.kouki.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SignUpViewModel {
    private val _mutableSignUpstate = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpstate

    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        _mutableSignUpstate.value = SignUpState.BadEmail
    }

}
