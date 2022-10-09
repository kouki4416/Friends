package com.kouki.friends.signup

import com.kouki.friends.InstantTaskExecutorExtension
import com.kouki.friends.app.TestDispatchers
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.user.UserRepository
import com.kouki.friends.domain.validation.RegexCredentialValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    private val regexCredentialValidator = RegexCredentialValidator()
    private val viewModel = SignUpViewModel(
        regexCredentialValidator,
        UserRepository(InMemoryUserCatalog()),
 TestDispatchers()
    )

    @Test
    fun accountCreated(){
        // Arrange
        val maya = User("mayaId", "maya@friends.com", "about Maya")

        // Act
        viewModel.createAccount(maya.email, "MaY@2021", maya.about)
        // Assert
        assertEquals(SignUpState.SignedUp(maya), checkAccountExists(viewModel))
    }

    private fun checkAccountExists(viewModel: SignUpViewModel) =
        viewModel.signUpState.value

    @Test
    fun anotherAccountCreated(){
        // Arrange
        val bob = User("bobId", "bob@friends.com", "about Bob")

        // Act
        viewModel.createAccount(bob.email, "Ple@seSubscribe1", bob.about)
        // Assert
        assertEquals(SignUpState.SignedUp(bob), viewModel.signUpState.value)
    }

    @Test
    fun createDuplicateAccount(){
        // Arrange
        val anna = User("annaId", "anna@friends.com", "about Anna")
        val password = "AnNaPas@123"
        val usersForPassword = mutableMapOf(password to mutableListOf(anna))
        val userRepository = UserRepository(InMemoryUserCatalog(usersForPassword))
        val viewModel = SignUpViewModel(
            regexCredentialValidator,
            userRepository,
            TestDispatchers()
        )
        // Act
        viewModel.createAccount(anna.email, password, anna.about)
        // Assert
        assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
    }


}