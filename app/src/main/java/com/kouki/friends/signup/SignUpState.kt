package com.kouki.friends.signup

import com.kouki.friends.domain.user.User

sealed class SignUpState {
    data class SignedUp(val user: User) : SignUpState()

    object Loading : SignUpState()
    object BadEmail : SignUpState()
    object BadPassword : SignUpState()
    object DuplicateAccount : SignUpState()
    object BackendError : SignUpState()
    object Offline : SignUpState()
}
