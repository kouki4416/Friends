package com.kouki.friends.signup

import androidx.lifecycle.viewmodel.viewModelFactory
import com.kouki.friends.InstantTaskExecutorExtension
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.validation.RegexCredentialValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated(){
        // Arrange
        val viewModel = SignUpViewModel(RegexCredentialValidator())
        // Act
        viewModel.createAccount("maya@friends.com", "MaY@2021", "about Maya")
        // Assert
        val maya = User("mayaId", "maya@friends.com", "about Maya")
        assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
    }
}