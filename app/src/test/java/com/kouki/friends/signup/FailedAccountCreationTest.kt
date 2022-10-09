package com.kouki.friends.signup

import com.kouki.friends.domain.exceptions.BackendException
import com.kouki.friends.domain.exceptions.ConnectionUnavailableException
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.user.UserCatalog
import com.kouki.friends.domain.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {


    @Test
    fun backendError() = runBlocking {
        // Arrange
        val userRepository = UserRepository(UnavailableUserCatalog())
        val result = userRepository.signUp(":email:", ":passoword:", ":about:")
        // Act
        // Assert
        assertEquals(SignUpState.BackendError, result)
    }

    @Test
    fun offlineError() = runBlocking{
        val userRepository = UserRepository(OfflineUserCatalog())
        val result = userRepository.signUp(":email:", ":passoword:", ":about:")

        assertEquals(SignUpState.Offline, result)
    }

    class OfflineUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }
    }


    class UnavailableUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }
    }

}