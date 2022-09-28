package com.kouki.friends.signup

import com.kouki.friends.InstantTaskExecutorExtension
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.UserRepository
import com.kouki.friends.domain.validation.CredentialsValidationResult
import com.kouki.friends.domain.validation.RegexCredentialValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialsValidationTest {
    @ParameterizedTest
    @CsvSource(
        "'email'",
        "'a@b.c'",
        "'ab@b.c'",
        "'ab@bc.c'",
        "''",
        "'   '",
    )
    fun invalidEmail(email: String) {
        val viewModel = SignUpViewModel (
            RegexCredentialValidator(),
            UserRepository(InMemoryUserCatalog())
        )
        viewModel.createAccount(email, ":password:", ":about:")
        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "'           '",
        "'12345678'",
        "'abcd5678'",
        "'abcDEF78'",
        "'abcdef78#$'",
        "'ABCDEF78#$'",
    )
    fun invalidPassword(password: String) {
        val viewModel = SignUpViewModel (
            RegexCredentialValidator(),
            UserRepository(InMemoryUserCatalog())
        )

        viewModel.createAccount("anna@friends.com", password, ":about:")

        assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
    }

    @Test
    fun validCredentials(){
        val validator = RegexCredentialValidator()

        val result = validator.validate("test@friends.com", "12ABcd3_!")

        assertEquals(CredentialsValidationResult.Valid, result)
    }

}