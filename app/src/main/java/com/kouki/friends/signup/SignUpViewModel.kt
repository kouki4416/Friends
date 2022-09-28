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
            is CredentialsValidationResult.Valid ->
                _mutableSignUpstate.value = signUp(email, password, about)
        }
    }

    val userCatalog = InMemoryUserCatalog()

    private fun signUp(
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

    class InMemoryUserCatalog(
        private val usersForPassword: MutableMap<String, MutableList<User>> = mutableMapOf()
    ) {
        fun createUser(
            email: String,
            password: String,
            about: String
        ): User {
            checkAccountExists(email)
            val userId = createUserIdFor(email)
            val user = User(userId, email, about)
            saveUser(password, user)
            return user
        }

        private fun saveUser(password: String, user: User) {
            usersForPassword.getOrPut(password, ::mutableListOf).add(user)
        }

        private fun createUserIdFor(email: String): String {
            return email.takeWhile { it != '@' } + "Id"
        }

        private fun checkAccountExists(email: String) {
            if (usersForPassword.values.flatten().any() { it.email == email }) {
                throw DuplicationAccountException()
            }
        }
    }

    class DuplicationAccountException : Throwable()

}
