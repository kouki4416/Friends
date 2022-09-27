package com.kouki.friends.signup

import com.kouki.friends.InstantTaskExecutorExtension
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.validation.RegexCredentialValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated(){
        // Arrange
        val maya = User("mayaId", "maya@friends.com", "about Maya")
        val viewModel = SignUpViewModel(RegexCredentialValidator())
        // Act
        viewModel.createAccount(maya.email, "MaY@2021", maya.about)
        // Assert
        assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
    }

    @Test
    fun anotherAccountCreated(){
        // Arrange
        val bob = User("bobId", "bob@friends.com", "about Bob")
        val viewModel = SignUpViewModel(RegexCredentialValidator())
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
        val viewModel = SignUpViewModel(RegexCredentialValidator()).also {
            it.createAccount(anna.email, password, anna.about )
        }
        // Act
        viewModel.createAccount(anna.email, password, anna.about)
        // Assert
        assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
    }


}