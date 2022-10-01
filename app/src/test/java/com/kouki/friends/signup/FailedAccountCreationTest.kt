package com.kouki.friends.signup

import com.kouki.friends.domain.exceptions.BackendException
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.user.UserCatalog
import com.kouki.friends.domain.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {


    @Test
    fun backendError() {
        // Arrange
        val userRepository = UserRepository(UnavailableUserCatalog())
        val result = userRepository.signUp("email", "passoword", "about")
        // Act
        // Assert
        assertEquals(SignUpState.BackendError, result)
    }

    class UnavailableUserCatalog : UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }

    }

}