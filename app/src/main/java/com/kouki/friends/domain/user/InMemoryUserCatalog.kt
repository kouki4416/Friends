package com.kouki.friends.domain.user

import com.kouki.friends.domain.exceptions.DuplicationAccountException
import kotlinx.coroutines.delay

class InMemoryUserCatalog(
    private val usersForPassword: MutableMap<String, MutableList<User>> = mutableMapOf()
) : UserCatalog {
    override suspend fun createUser(
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

    private fun checkAccountExists(email: String) {
        if (usersForPassword.values.flatten().any() { it.email == email }) {
            throw DuplicationAccountException()
        }
    }

    private fun saveUser(password: String, user: User) {
        usersForPassword.getOrPut(password, ::mutableListOf).add(user)
    }

    private fun createUserIdFor(email: String): String {
        return email.takeWhile { it != '@' } + "Id"
    }

}