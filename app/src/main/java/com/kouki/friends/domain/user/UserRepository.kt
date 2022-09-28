package com.kouki.friends.domain.user

import com.kouki.friends.domain.exceptions.DuplicationAccountException
import com.kouki.friends.signup.SignUpState

class UserRepository(
    private val userCatalog: InMemoryUserCatalog = InMemoryUserCatalog()
) {

    fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            SignUpState.SignedUp(user)
        } catch (duplicateAccount: DuplicationAccountException) {
            SignUpState.DuplicateAccount
        }
    }
}