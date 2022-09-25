package com.kouki.friends.signup

sealed class SignUpState {
    object BadEmail: SignUpState()
    object BadPassword: SignUpState()
}
