package com.kouki.friends.signup

import com.kouki.friends.InstantTaskExecutorExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialsValidationTest {
    @Test
    fun invalidEmail() {
        val viewModel = SignUpViewModel()
        viewModel.createAccount("email", ":password:", ":about:")
        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
    }

}